# Ubuntu部署指南

## 📋 服务器准备

### 1. 系统要求
- **操作系统**: Ubuntu 20.04 LTS 或更高版本
- **内存**: 最少 4GB，推荐 8GB
- **存储**: 最少 20GB 可用空间
- **CPU**: 最少 2 核心
- **网络**: 公网IP（如需要外网访问）

### 2. 更新系统
```bash
# 更新包列表
sudo apt update

# 升级系统
sudo apt upgrade -y

# 安装必要工具
sudo apt install -y curl wget vim git unzip
```

## 🗄️ 数据库安装配置

### 1. 安装MySQL
```bash
# 安装MySQL
sudo apt install -y mysql-server

# 启动MySQL服务
sudo systemctl start mysql
sudo systemctl enable mysql

# 安全配置
sudo mysql_secure_installation
```

### 2. 配置MySQL
```bash
# 登录MySQL
sudo mysql -u root -p

# 创建数据库
CREATE DATABASE `ruoyi-vue-pro` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 创建用户
CREATE USER 'yudao'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON `ruoyi-vue-pro`.* TO 'yudao'@'localhost';
FLUSH PRIVILEGES;

# 退出
EXIT;
```

### 3. 导入数据库
```bash
# 上传SQL文件到服务器
scp sql/mysql/*.sql user@server:/tmp/

# 导入数据库
mysql -u yudao -p ruoyi-vue-pro < /tmp/mis-system-ruoyi-vue-pro-*.sql
```

## 🔴 Redis安装配置

### 1. 安装Redis
```bash
# 安装Redis
sudo apt install -y redis-server

# 启动Redis服务
sudo systemctl start redis-server
sudo systemctl enable redis-server

# 检查状态
sudo systemctl status redis-server
```

### 2. 配置Redis
```bash
# 编辑配置文件
sudo vim /etc/redis/redis.conf

# 修改以下配置
bind 127.0.0.1
port 6379
# requirepass your_redis_password  # 如需要密码
```

```bash
# 重启Redis
sudo systemctl restart redis-server

# 测试连接
redis-cli ping
```

## ☕ Java环境安装

### 1. 安装OpenJDK
```bash
# 安装OpenJDK 8
sudo apt install -y openjdk-8-jdk

# 或者安装OpenJDK 11
sudo apt install -y openjdk-11-jdk

# 检查版本
java -version
javac -version
```

### 2. 配置JAVA_HOME
```bash
# 查找Java安装路径
sudo update-alternatives --config java

# 编辑环境变量
sudo vim /etc/environment

# 添加以下内容
JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"
PATH="$PATH:$JAVA_HOME/bin"

# 重新加载环境变量
source /etc/environment
```

## 🌐 Nginx安装配置

### 1. 安装Nginx
```bash
# 安装Nginx
sudo apt install -y nginx

# 启动Nginx
sudo systemctl start nginx
sudo systemctl enable nginx

# 检查状态
sudo systemctl status nginx
```

### 2. 配置Nginx
```bash
# 创建配置文件
sudo vim /etc/nginx/sites-available/yudao

# 配置内容
server {
    listen 80;
    server_name your-domain.com;  # 替换为你的域名或IP
    
    # 前端静态文件
    location / {
        root /opt/nginx/html/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    # 后端API代理
    location /admin-api/ {
        proxy_pass http://127.0.0.1:48080/admin-api/;
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

# 启用配置
sudo ln -s /etc/nginx/sites-available/yudao /etc/nginx/sites-enabled/

# 测试配置
sudo nginx -t

# 重启Nginx
sudo systemctl restart nginx
```

## 📁 应用部署

### 1. 创建目录结构
```bash
# 创建应用目录
sudo mkdir -p /opt/app
sudo mkdir -p /opt/nginx/html
sudo mkdir -p /opt/logs

# 设置权限
sudo chown -R $USER:$USER /opt/app
sudo chown -R $USER:$USER /opt/nginx/html
sudo chown -R $USER:$USER /opt/logs
```

### 2. 上传应用文件
```bash
# 上传后端jar包
scp yudao-server.jar user@server:/opt/app/

# 上传前端静态文件
scp -r dist/ user@server:/opt/nginx/html/
```

### 3. 创建启动脚本
```bash
# 创建后端启动脚本
sudo vim /opt/app/start.sh

# 脚本内容
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

# 设置执行权限
sudo chmod +x /opt/app/start.sh
```

### 4. 创建停止脚本
```bash
# 创建停止脚本
sudo vim /opt/app/stop.sh

# 脚本内容
#!/bin/bash
if [ -f /opt/app/app.pid ]; then
    PID=$(cat /opt/app/app.pid)
    kill -9 $PID
    rm -f /opt/app/app.pid
    echo "应用已停止"
else
    echo "应用未运行"
fi

# 设置执行权限
sudo chmod +x /opt/app/stop.sh
```

## 🚀 启动服务

### 1. 启动后端服务
```bash
# 启动应用
/opt/app/start.sh

# 检查进程
ps aux | grep yudao-server

# 查看日志
tail -f /opt/logs/app.log

# 检查端口
netstat -tlnp | grep 48080
```

### 2. 验证服务
```bash
# 检查后端健康状态
curl http://localhost:48080/actuator/health

# 检查前端访问
curl http://localhost/

# 检查Nginx状态
sudo systemctl status nginx
```

## 🔧 系统服务配置

### 1. 创建systemd服务
```bash
# 创建服务文件
sudo vim /etc/systemd/system/yudao.service

# 服务配置
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

# 重新加载systemd
sudo systemctl daemon-reload

# 启用服务
sudo systemctl enable yudao

# 启动服务
sudo systemctl start yudao

# 检查状态
sudo systemctl status yudao
```

## 🔒 防火墙配置

### 1. 配置UFW防火墙
```bash
# 启用防火墙
sudo ufw enable

# 允许SSH
sudo ufw allow ssh

# 允许HTTP
sudo ufw allow 80

# 允许HTTPS
sudo ufw allow 443

# 查看状态
sudo ufw status
```

### 2. 配置iptables（可选）
```bash
# 允许必要端口
sudo iptables -A INPUT -p tcp --dport 22 -j ACCEPT
sudo iptables -A INPUT -p tcp --dport 80 -j ACCEPT
sudo iptables -A INPUT -p tcp --dport 443 -j ACCEPT

# 保存规则
sudo iptables-save > /etc/iptables/rules.v4
```

## 📊 监控配置

### 1. 日志轮转
```bash
# 创建logrotate配置
sudo vim /etc/logrotate.d/yudao

# 配置内容
/opt/logs/*.log {
    daily
    missingok
    rotate 30
    compress
    delaycompress
    notifempty
    create 644 root root
    postrotate
        /bin/kill -USR1 $(cat /opt/app/app.pid 2>/dev/null) 2>/dev/null || true
    endscript
}
```

### 2. 系统监控
```bash
# 安装htop
sudo apt install -y htop

# 安装iotop
sudo apt install -y iotop

# 监控命令
htop                    # 系统资源监控
iotop                   # 磁盘IO监控
df -h                   # 磁盘空间
free -h                 # 内存使用
netstat -tlnp           # 端口监听
```

## 🔍 故障排查

### 1. 常见问题
```bash
# 检查服务状态
sudo systemctl status yudao
sudo systemctl status nginx
sudo systemctl status mysql
sudo systemctl status redis-server

# 查看日志
sudo journalctl -u yudao -f
sudo tail -f /opt/logs/app.log
sudo tail -f /var/log/nginx/error.log

# 检查端口占用
sudo netstat -tlnp | grep :48080
sudo netstat -tlnp | grep :80
```

### 2. 性能优化
```bash
# 调整系统参数
sudo vim /etc/sysctl.conf

# 添加以下配置
net.core.somaxconn = 65535
net.ipv4.tcp_max_syn_backlog = 65535
net.ipv4.tcp_fin_timeout = 10
net.ipv4.tcp_tw_reuse = 1

# 应用配置
sudo sysctl -p
```

## ✅ 部署检查清单

### 环境检查
- [ ] Ubuntu系统版本正确
- [ ] 系统已更新到最新
- [ ] 必要工具已安装

### 服务检查
- [ ] MySQL已安装并运行
- [ ] Redis已安装并运行
- [ ] Java环境已配置
- [ ] Nginx已安装并运行

### 应用检查
- [ ] 数据库已创建并导入数据
- [ ] 后端jar包已上传
- [ ] 前端静态文件已上传
- [ ] 配置文件已正确设置

### 网络检查
- [ ] 防火墙已配置
- [ ] 端口已开放
- [ ] 域名解析正确（如使用域名）

### 功能检查
- [ ] 后端服务启动成功
- [ ] 前端页面可访问
- [ ] 登录功能正常
- [ ] API接口调用正常

---

**注意**: 部署完成后请进行全面的功能测试，确保系统正常运行。
