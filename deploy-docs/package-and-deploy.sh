#!/bin/bash

# 选煤厂生产管理系统 - 打包部署脚本
# 自动打包前后端并部署到服务器

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 配置变量
SERVER_HOST="your-server-ip"
SERVER_USER="root"
SERVER_PORT="22"
BACKEND_JAR="yudao-server.jar"
FRONTEND_DIST="dist"

# 日志函数
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查依赖
check_dependencies() {
    log_info "检查依赖..."
    
    # 检查Java
    if ! command -v java &> /dev/null; then
        log_error "Java未安装，请先安装Java 8或更高版本"
        exit 1
    fi
    
    # 检查Maven
    if ! command -v mvn &> /dev/null; then
        log_error "Maven未安装，请先安装Maven"
        exit 1
    fi
    
    # 检查Node.js
    if ! command -v node &> /dev/null; then
        log_error "Node.js未安装，请先安装Node.js 16或更高版本"
        exit 1
    fi
    
    # 检查pnpm
    if ! command -v pnpm &> /dev/null; then
        log_warning "pnpm未安装，将使用npm"
    fi
    
    log_success "依赖检查完成"
}

# 打包后端
package_backend() {
    log_info "开始打包后端..."
    
    cd yudao-server
    
    # 清理之前的构建
    mvn clean
    
    # 打包
    mvn package -DskipTests
    
    # 检查jar包是否生成
    if [ ! -f "target/yudao-server.jar" ]; then
        log_error "后端打包失败，jar包未生成"
        exit 1
    fi
    
    # 复制jar包到项目根目录
    cp target/yudao-server.jar ../$BACKEND_JAR
    
    cd ..
    log_success "后端打包完成: $BACKEND_JAR"
}

# 打包前端
package_frontend() {
    log_info "开始打包前端..."
    
    cd yudao-ui/yudao-ui-admin-vue3
    
    # 检查环境配置文件
    if [ ! -f ".env.prod" ]; then
        log_warning "未找到 .env.prod 文件，将使用默认配置"
        # 创建生产环境配置
        cat > .env.prod << 'EOF'
VITE_APP_TITLE=选煤厂生产管理系统
VITE_APP_TENANT_ENABLE=true
VITE_APP_DEFAULT_LOGIN_TENANT=芋道源码
VITE_BASE_URL=http://your-server-ip
VITE_API_URL=/admin-api
VITE_BASE_PATH=/
VITE_APP_CAPTCHA_ENABLE=true
VITE_APP_DEFAULT_LOGIN_USERNAME=admin
VITE_APP_DEFAULT_LOGIN_PASSWORD=admin123
VITE_APP_DOCALERT_ENABLE=false
VITE_DROP_DEBUGGER=true
VITE_DROP_CONSOLE=true
VITE_SOURCEMAP=false
VITE_OUT_DIR=dist
EOF
    fi
    
    # 安装依赖
    if command -v pnpm &> /dev/null; then
        pnpm install
        pnpm run build:prod
    else
        npm install
        npm run build:prod
    fi
    
    # 检查dist目录是否生成
    if [ ! -d "dist" ]; then
        log_error "前端打包失败，dist目录未生成"
        exit 1
    fi
    
    # 复制dist目录到项目根目录
    cp -r dist ../../$FRONTEND_DIST
    
    cd ../..
    log_success "前端打包完成: $FRONTEND_DIST"
}

# 上传到服务器
upload_to_server() {
    log_info "上传文件到服务器..."
    
    # 检查SSH连接
    if ! ssh -o ConnectTimeout=10 -p $SERVER_PORT $SERVER_USER@$SERVER_HOST "echo 'SSH连接成功'" &> /dev/null; then
        log_error "无法连接到服务器 $SERVER_HOST"
        exit 1
    fi
    
    # 创建服务器目录
    ssh -p $SERVER_PORT $SERVER_USER@$SERVER_HOST "mkdir -p /opt/app /opt/nginx/html"
    
    # 上传后端jar包
    log_info "上传后端jar包..."
    scp -P $SERVER_PORT $BACKEND_JAR $SERVER_USER@$SERVER_HOST:/opt/app/
    
    # 上传前端静态文件
    log_info "上传前端静态文件..."
    scp -r -P $SERVER_PORT $FRONTEND_DIST $SERVER_USER@$SERVER_HOST:/opt/nginx/html/
    
    log_success "文件上传完成"
}

# 部署到服务器
deploy_to_server() {
    log_info "在服务器上部署应用..."
    
    # 停止现有服务
    ssh -p $SERVER_PORT $SERVER_USER@$SERVER_HOST "systemctl stop yudao || true"
    
    # 备份旧版本
    ssh -p $SERVER_PORT $SERVER_USER@$SERVER_HOST "
        if [ -f /opt/app/yudao-server.jar ]; then
            cp /opt/app/yudao-server.jar /opt/app/yudao-server.jar.backup.$(date +%Y%m%d_%H%M%S)
        fi
        if [ -d /opt/nginx/html/dist ]; then
            cp -r /opt/nginx/html/dist /opt/nginx/html/dist.backup.$(date +%Y%m%d_%H%M%S)
        fi
    "
    
    # 重启服务
    ssh -p $SERVER_PORT $SERVER_USER@$SERVER_HOST "
        systemctl start yudao
        systemctl restart nginx
    "
    
    # 检查服务状态
    sleep 10
    if ssh -p $SERVER_PORT $SERVER_USER@$SERVER_HOST "systemctl is-active --quiet yudao"; then
        log_success "服务启动成功"
    else
        log_error "服务启动失败，请检查日志"
        ssh -p $SERVER_PORT $SERVER_USER@$SERVER_HOST "journalctl -u yudao -n 20"
        exit 1
    fi
}

# 验证部署
verify_deployment() {
    log_info "验证部署..."
    
    # 检查后端健康状态
    if curl -f -s "http://$SERVER_HOST:48080/actuator/health" > /dev/null; then
        log_success "后端服务健康检查通过"
    else
        log_warning "后端服务健康检查失败"
    fi
    
    # 检查前端访问
    if curl -f -s "http://$SERVER_HOST/" > /dev/null; then
        log_success "前端页面访问正常"
    else
        log_warning "前端页面访问失败"
    fi
    
    log_info "部署验证完成"
    log_info "访问地址: http://$SERVER_HOST"
}

# 清理临时文件
cleanup() {
    log_info "清理临时文件..."
    rm -f $BACKEND_JAR
    rm -rf $FRONTEND_DIST
    log_success "清理完成"
}

# 显示帮助信息
show_help() {
    echo "选煤厂生产管理系统 - 打包部署脚本"
    echo ""
    echo "用法: $0 [选项]"
    echo ""
    echo "选项:"
    echo "  -h, --help     显示帮助信息"
    echo "  -s, --server   服务器地址 (默认: your-server-ip)"
    echo "  -u, --user     服务器用户 (默认: root)"
    echo "  -p, --port     SSH端口 (默认: 22)"
    echo "  --package-only 仅打包，不上传部署"
    echo "  --upload-only  仅上传部署，不重新打包"
    echo ""
    echo "示例:"
    echo "  $0                                    # 使用默认配置"
    echo "  $0 -s 192.168.1.100 -u ubuntu        # 指定服务器和用户"
    echo "  $0 --package-only                     # 仅打包"
    echo "  $0 --upload-only                      # 仅上传部署"
}

# 解析命令行参数
PACKAGE_ONLY=false
UPLOAD_ONLY=false

while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            show_help
            exit 0
            ;;
        -s|--server)
            SERVER_HOST="$2"
            shift 2
            ;;
        -u|--user)
            SERVER_USER="$2"
            shift 2
            ;;
        -p|--port)
            SERVER_PORT="$2"
            shift 2
            ;;
        --package-only)
            PACKAGE_ONLY=true
            shift
            ;;
        --upload-only)
            UPLOAD_ONLY=true
            shift
            ;;
        *)
            log_error "未知参数: $1"
            show_help
            exit 1
            ;;
    esac
done

# 主函数
main() {
    log_info "开始打包部署选煤厂生产管理系统..."
    log_info "服务器: $SERVER_HOST:$SERVER_PORT"
    log_info "用户: $SERVER_USER"
    
    check_dependencies
    
    if [ "$UPLOAD_ONLY" = false ]; then
        package_backend
        package_frontend
    fi
    
    if [ "$PACKAGE_ONLY" = false ]; then
        upload_to_server
        deploy_to_server
        verify_deployment
    fi
    
    if [ "$PACKAGE_ONLY" = false ] && [ "$UPLOAD_ONLY" = false ]; then
        cleanup
    fi
    
    log_success "部署完成！"
    log_info "访问地址: http://$SERVER_HOST"
}

# 运行主函数
main "$@"
