# 选煤厂生产管理系统 - 部署文档

## 📁 文档目录

- [后端打包指南](./backend-package-guide.md) - 如何打包Java后端项目
- [前端打包指南](./frontend-package-guide.md) - 如何打包Vue3前端项目  
- [Ubuntu部署指南](./ubuntu-deploy-guide.md) - 在Ubuntu服务器上部署系统
- [Docker部署指南](./docker-deploy-guide.md) - 使用Docker容器化部署
- [环境配置文件](./env-configs/) - 各种环境的配置文件

## 🚀 快速开始

### 1. 后端打包
```bash
# 进入后端项目目录
cd yudao-server

# 使用Maven打包
mvn clean package -DskipTests

# 生成的jar包位置
target/yudao-server.jar
```

### 2. 前端打包
```bash
# 进入前端项目目录
cd yudao-ui/yudao-ui-admin-vue3

# 安装依赖
npm install

# 生产环境打包
npm run build:prod

# 生成的静态文件位置
dist/
```

### 3. 部署到Ubuntu
```bash
# 1. 上传文件到服务器
scp yudao-server.jar user@server:/opt/app/
scp -r dist/ user@server:/opt/nginx/html/

# 2. 配置数据库和Redis
# 3. 启动后端服务
# 4. 配置Nginx
```

## 📋 系统要求

### 服务器要求
- **操作系统**: Ubuntu 20.04 LTS 或更高版本
- **内存**: 最少 4GB，推荐 8GB
- **存储**: 最少 20GB 可用空间
- **CPU**: 最少 2 核心

### 软件要求
- **Java**: JDK 1.8 或更高版本
- **MySQL**: 5.7 或 8.0
- **Redis**: 6.0 或更高版本
- **Nginx**: 1.18 或更高版本
- **Node.js**: 16.0 或更高版本（仅打包时需要）

## 🔧 配置文件

### 环境变量配置
- 开发环境: `.env.dev`
- 测试环境: `.env.test`  
- 生产环境: `.env.prod`

### 数据库配置
- 数据库连接配置在 `application.yaml` 中
- 需要根据实际环境修改数据库连接信息

## 📞 技术支持

如果在部署过程中遇到问题，请检查：
1. 服务器环境是否满足要求
2. 数据库连接是否正常
3. 端口是否被占用
4. 防火墙设置是否正确

---

**注意**: 部署前请确保已备份重要数据，并在测试环境中验证部署流程。
