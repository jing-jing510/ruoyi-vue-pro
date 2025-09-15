#!/bin/bash

# 选煤厂生产管理系统 - 快速部署脚本
# 适用于Ubuntu 20.04 LTS

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

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

# 检查是否为root用户
check_root() {
    if [[ $EUID -ne 0 ]]; then
        log_error "此脚本需要root权限运行"
        exit 1
    fi
}

# 更新系统
update_system() {
    log_info "更新系统包..."
    apt update && apt upgrade -y
    apt install -y curl wget vim git unzip software-properties-common
    log_success "系统更新完成"
}

# 安装Java
install_java() {
    log_info "安装Java环境..."
    apt install -y openjdk-8-jdk
    echo 'JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"' >> /etc/environment
    echo 'PATH="$PATH:$JAVA_HOME/bin"' >> /etc/environment
    source /etc/environment
    log_success "Java安装完成: $(java -version 2>&1 | head -n 1)"
}

# 安装MySQL
install_mysql() {
    log_info "安装MySQL数据库..."
    apt install -y mysql-server
    systemctl start mysql
    systemctl enable mysql
    
    # 安全配置
    mysql -e "CREATE DATABASE \`ruoyi-vue-pro\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
    mysql -e "CREATE USER 'yudao'@'localhost' IDENTIFIED BY 'yudao123';"
    mysql -e "GRANT ALL PRIVILEGES ON \`ruoyi-vue-pro\`.* TO 'yudao'@'localhost';"
    mysql -e "FLUSH PRIVILEGES;"
    
    log_success "MySQL安装完成"
}

# 安装Redis
install_redis() {
    log_info "安装Redis缓存..."
    apt install -y redis-server
    systemctl start redis-server
    systemctl enable redis-server
    log_success "Redis安装完成"
}

# 安装Nginx
install_nginx() {
    log_info "安装Nginx..."
    apt install -y nginx
    systemctl start nginx
    systemctl enable nginx
    log_success "Nginx安装完成"
}

# 创建应用目录
create_directories() {
    log_info "创建应用目录..."
    mkdir -p /opt/app
    mkdir -p /opt/nginx/html
    mkdir -p /opt/logs
    chown -R $SUDO_USER:$SUDO_USER /opt/app
    chown -R $SUDO_USER:$SUDO_USER /opt/nginx/html
    chown -R $SUDO_USER:$SUDO_USER /opt/logs
    log_success "应用目录创建完成"
}

# 配置防火墙
configure_firewall() {
    log_info "配置防火墙..."
    ufw --force enable
    ufw allow ssh
    ufw allow 80
    ufw allow 443
    log_success "防火墙配置完成"
}

# 创建启动脚本
create_startup_scripts() {
    log_info "创建启动脚本..."
    
    # 启动脚本
    cat > /opt/app/start.sh << 'EOF'
#!/bin/bash
cd /opt/app
nohup java -server \
  -Xms1g -Xmx2g \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/opt/logs/ \
  -jar yudao-server.jar \
  --spring.profiles.active=prod \
  > /opt/logs/app.log 2>&1 &

echo $! > /opt/app/app.pid
echo "应用启动成功，PID: $(cat /opt/app/app.pid)"
EOF

    # 停止脚本
    cat > /opt/app/stop.sh << 'EOF'
#!/bin/bash
if [ -f /opt/app/app.pid ]; then
    PID=$(cat /opt/app/app.pid)
    kill -9 $PID
    rm -f /opt/app/app.pid
    echo "应用已停止"
else
    echo "应用未运行"
fi
EOF

    chmod +x /opt/app/start.sh
    chmod +x /opt/app/stop.sh
    log_success "启动脚本创建完成"
}

# 配置Nginx
configure_nginx() {
    log_info "配置Nginx..."
    
    cat > /etc/nginx/sites-available/yudao << 'EOF'
server {
    listen 80;
    server_name _;
    
    location / {
        root /opt/nginx/html/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    location /admin-api/ {
        proxy_pass http://127.0.0.1:48080/admin-api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
    
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
}
EOF

    ln -sf /etc/nginx/sites-available/yudao /etc/nginx/sites-enabled/
    rm -f /etc/nginx/sites-enabled/default
    nginx -t
    systemctl restart nginx
    log_success "Nginx配置完成"
}

# 创建systemd服务
create_systemd_service() {
    log_info "创建systemd服务..."
    
    cat > /etc/systemd/system/yudao.service << 'EOF'
[Unit]
Description=YuDao Server
After=network.target mysql.service redis.service

[Service]
Type=forking
User=root
Group=root
WorkingDirectory=/opt/app
ExecStart=/opt/app/start.sh
ExecStop=/opt/app/stop.sh
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

    systemctl daemon-reload
    systemctl enable yudao
    log_success "systemd服务创建完成"
}

# 主函数
main() {
    log_info "开始部署选煤厂生产管理系统..."
    
    check_root
    update_system
    install_java
    install_mysql
    install_redis
    install_nginx
    create_directories
    configure_firewall
    create_startup_scripts
    configure_nginx
    create_systemd_service
    
    log_success "系统环境部署完成！"
    log_info "接下来请："
    log_info "1. 上传后端jar包到 /opt/app/"
    log_info "2. 上传前端静态文件到 /opt/nginx/html/"
    log_info "3. 导入数据库文件"
    log_info "4. 启动服务: systemctl start yudao"
    log_info "5. 访问系统: http://your-server-ip"
}

# 运行主函数
main "$@"
