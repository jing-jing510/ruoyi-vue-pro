# Docker部署指南

## 📋 准备工作

### 1. 环境要求
- **Docker**: 20.10 或更高版本
- **Docker Compose**: 2.0 或更高版本
- **系统**: Ubuntu 20.04 LTS 或更高版本

### 2. 安装Docker
```bash
# 更新包列表
sudo apt update

# 安装必要依赖
sudo apt install -y apt-transport-https ca-certificates curl gnupg lsb-release

# 添加Docker官方GPG密钥
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# 添加Docker仓库
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# 安装Docker
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io

# 启动Docker服务
sudo systemctl start docker
sudo systemctl enable docker

# 将当前用户添加到docker组
sudo usermod -aG docker $USER

# 重新登录或执行
newgrp docker

# 验证安装
docker --version
docker-compose --version
```

## 🐳 Docker镜像构建

### 1. 后端Dockerfile
```dockerfile
# 创建文件: yudao-server/Dockerfile
FROM openjdk:8-jre-alpine

# 设置工作目录
WORKDIR /app

# 复制jar包
COPY target/yudao-server.jar app.jar

# 创建日志目录
RUN mkdir -p /app/logs

# 暴露端口
EXPOSE 48080

# 设置JVM参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

### 2. 前端Dockerfile
```dockerfile
# 创建文件: yudao-ui/yudao-ui-admin-vue3/Dockerfile
# 构建阶段
FROM node:16-alpine as build-stage

# 设置工作目录
WORKDIR /app

# 复制package文件
COPY package*.json ./
COPY pnpm-lock.yaml ./

# 安装pnpm
RUN npm install -g pnpm

# 安装依赖
RUN pnpm install

# 复制源代码
COPY . .

# 构建应用
RUN pnpm run build:prod

# 生产阶段
FROM nginx:alpine as production-stage

# 复制构建结果
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 复制nginx配置
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露端口
EXPOSE 80

# 启动nginx
CMD ["nginx", "-g", "daemon off;"]
```

### 3. 前端Nginx配置
```nginx
# 创建文件: yudao-ui/yudao-ui-admin-vue3/nginx.conf
server {
    listen 80;
    server_name localhost;
    
    # 前端静态文件
    location / {
        root /usr/share/nginx/html;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    # 后端API代理
    location /admin-api/ {
        proxy_pass http://yudao-server:48080/admin-api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
    
    # 静态资源缓存
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
}
```

## 🐙 Docker Compose配置

### 1. 主配置文件
```yaml
# 创建文件: docker-compose.yml
version: '3.8'

services:
  # MySQL数据库
  mysql:
    image: mysql:8.0
    container_name: yudao-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_DATABASE: ruoyi-vue-pro
      MYSQL_USER: yudao
      MYSQL_PASSWORD: yudao123
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./sql/mysql:/docker-entrypoint-initdb.d
    command: --default-authentication-plugin=mysql_native_password
    networks:
      - yudao-network

  # Redis缓存
  redis:
    image: redis:6.2-alpine
    container_name: yudao-redis
    restart: always
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    command: redis-server --appendonly yes
    networks:
      - yudao-network

  # 后端服务
  yudao-server:
    build:
      context: ./yudao-server
      dockerfile: Dockerfile
    container_name: yudao-server
    restart: always
    ports:
      - "48080:48080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - MYSQL_HOST=mysql
      - MYSQL_PORT=3306
      - MYSQL_DATABASE=ruoyi-vue-pro
      - MYSQL_USERNAME=yudao
      - MYSQL_PASSWORD=yudao123
      - REDIS_HOST=redis
      - REDIS_PORT=6379
    volumes:
      - ./logs:/app/logs
    depends_on:
      - mysql
      - redis
    networks:
      - yudao-network

  # 前端服务
  yudao-ui:
    build:
      context: ./yudao-ui/yudao-ui-admin-vue3
      dockerfile: Dockerfile
    container_name: yudao-ui
    restart: always
    ports:
      - "80:80"
    depends_on:
      - yudao-server
    networks:
      - yudao-network

volumes:
  mysql_data:
  redis_data:

networks:
  yudao-network:
    driver: bridge
```

### 2. 环境配置文件
```yaml
# 创建文件: docker-compose.override.yml
version: '3.8'

services:
  yudao-server:
    environment:
      - JAVA_OPTS=-Xms1g -Xmx2g -XX:+UseG1GC
    volumes:
      - ./config/application-docker.yaml:/app/config/application-docker.yaml
```

## 🔧 配置文件

### 1. 后端Docker环境配置
```yaml
# 创建文件: yudao-server/src/main/resources/application-docker.yaml
spring:
  datasource:
    master:
      url: jdbc:mysql://${MYSQL_HOST:mysql}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:ruoyi-vue-pro}?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true
      username: ${MYSQL_USERNAME:yudao}
      password: ${MYSQL_PASSWORD:yudao123}
    slave:
      url: jdbc:mysql://${MYSQL_HOST:mysql}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:ruoyi-vue-pro}?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true
      username: ${MYSQL_USERNAME:yudao}
      password: ${MYSQL_PASSWORD:yudao123}
  
  redis:
    host: ${REDIS_HOST:redis}
    port: ${REDIS_PORT:6379}
    database: 1

server:
  port: 48080

yudao:
  tenant:
    enable: true
```

### 2. 前端环境配置
```bash
# 创建文件: yudao-ui/yudao-ui-admin-vue3/.env.docker
VITE_APP_TITLE=选煤厂生产管理系统
VITE_APP_TENANT_ENABLE=true
VITE_APP_DEFAULT_LOGIN_TENANT=芋道源码
VITE_BASE_URL=http://localhost
VITE_API_URL=/admin-api
VITE_BASE_PATH=/
VITE_APP_CAPTCHA_ENABLE=true
VITE_APP_DEFAULT_LOGIN_USERNAME=admin
VITE_APP_DEFAULT_LOGIN_PASSWORD=admin123
```

## 🚀 部署步骤

### 1. 准备文件
```bash
# 创建部署目录
mkdir yudao-docker-deploy
cd yudao-docker-deploy

# 复制项目文件
cp -r ../yudao-server ./
cp -r ../yudao-ui ./
cp -r ../sql ./

# 创建Dockerfile和配置文件
# (按照上面的内容创建相应文件)
```

### 2. 构建镜像
```bash
# 构建所有服务
docker-compose build

# 或者单独构建
docker-compose build yudao-server
docker-compose build yudao-ui
```

### 3. 启动服务
```bash
# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f
```

### 4. 验证部署
```bash
# 检查容器状态
docker ps

# 检查服务健康状态
curl http://localhost/actuator/health

# 访问前端
curl http://localhost/
```

## 🔍 管理命令

### 1. 常用命令
```bash
# 启动服务
docker-compose up -d

# 停止服务
docker-compose down

# 重启服务
docker-compose restart

# 查看日志
docker-compose logs -f [service_name]

# 进入容器
docker-compose exec [service_name] sh

# 更新服务
docker-compose pull
docker-compose up -d
```

### 2. 数据备份
```bash
# 备份MySQL数据
docker-compose exec mysql mysqldump -u root -p123456 ruoyi-vue-pro > backup.sql

# 备份Redis数据
docker-compose exec redis redis-cli BGSAVE

# 备份整个数据卷
docker run --rm -v yudao-docker-deploy_mysql_data:/data -v $(pwd):/backup alpine tar czf /backup/mysql_backup.tar.gz -C /data .
```

### 3. 数据恢复
```bash
# 恢复MySQL数据
docker-compose exec -T mysql mysql -u root -p123456 ruoyi-vue-pro < backup.sql

# 恢复数据卷
docker run --rm -v yudao-docker-deploy_mysql_data:/data -v $(pwd):/backup alpine tar xzf /backup/mysql_backup.tar.gz -C /data
```

## 📊 监控配置

### 1. 添加监控服务
```yaml
# 在docker-compose.yml中添加
  # 监控服务
  prometheus:
    image: prom/prometheus:latest
    container_name: yudao-prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml
    networks:
      - yudao-network

  grafana:
    image: grafana/grafana:latest
    container_name: yudao-grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana_data:/var/lib/grafana
    networks:
      - yudao-network
```

### 2. 日志收集
```yaml
  # 日志收集
  filebeat:
    image: elastic/filebeat:7.15.0
    container_name: yudao-filebeat
    volumes:
      - ./logs:/var/log/yudao
      - ./monitoring/filebeat.yml:/usr/share/filebeat/filebeat.yml
    networks:
      - yudao-network
```

## 🔒 安全配置

### 1. 网络安全
```yaml
# 创建文件: docker-compose.prod.yml
version: '3.8'

services:
  mysql:
    ports: []  # 不暴露端口到主机
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}

  redis:
    ports: []  # 不暴露端口到主机
    command: redis-server --requirepass ${REDIS_PASSWORD}
```

### 2. 环境变量文件
```bash
# 创建文件: .env
MYSQL_ROOT_PASSWORD=your_strong_password
MYSQL_PASSWORD=your_strong_password
REDIS_PASSWORD=your_strong_password
```

## ✅ 部署检查清单

### 环境检查
- [ ] Docker已安装并运行
- [ ] Docker Compose已安装
- [ ] 项目文件已准备完整

### 配置检查
- [ ] Dockerfile配置正确
- [ ] docker-compose.yml配置正确
- [ ] 环境变量配置正确
- [ ] 数据库配置正确

### 部署检查
- [ ] 镜像构建成功
- [ ] 容器启动成功
- [ ] 服务健康检查通过
- [ ] 前端页面可访问
- [ ] 后端API可调用

### 功能检查
- [ ] 登录功能正常
- [ ] 数据库连接正常
- [ ] Redis缓存正常
- [ ] 文件上传正常

---

**注意**: Docker部署适合快速部署和开发环境，生产环境建议使用更详细的配置和监控。
