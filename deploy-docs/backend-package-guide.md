# 后端打包指南

## 📋 准备工作

### 1. 环境要求
- **JDK**: 1.8 或更高版本
- **Maven**: 3.6 或更高版本
- **MySQL**: 5.7 或 8.0
- **Redis**: 6.0 或更高版本

### 2. 检查项目结构
确保项目结构完整：
```
yudao-server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
├── pom.xml
└── target/
```

## 🔧 配置修改

### 1. 修改数据库配置
编辑 `yudao-server/src/main/resources/application.yaml`：

```yaml
spring:
  datasource:
    # 主数据源
    master:
      url: jdbc:mysql://localhost:3306/ruoyi-vue-pro?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true
      username: root
      password: 123456
    # 从数据源（如果使用读写分离）
    slave:
      url: jdbc:mysql://localhost:3306/ruoyi-vue-pro?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true
      username: root
      password: 123456

  # Redis配置
  redis:
    host: localhost
    port: 6379
    password: # 如果有密码
    database: 1
```

### 2. 修改应用配置
```yaml
server:
  port: 48080  # 服务端口

# 多租户配置
yudao:
  tenant:
    enable: true  # 是否启用多租户
```

## 📦 打包步骤

### 1. 清理项目
```bash
# 进入后端项目目录
cd yudao-server

# 清理之前的构建
mvn clean
```

### 2. 编译项目
```bash
# 编译项目（跳过测试）
mvn compile -DskipTests

# 或者运行测试
mvn test
```

### 3. 打包项目
```bash
# 打包为可执行jar
mvn package -DskipTests

# 或者使用Spring Boot插件打包
mvn spring-boot:repackage -DskipTests
```

### 4. 验证打包结果
```bash
# 检查生成的jar包
ls -la target/yudao-server.jar

# 查看jar包信息
java -jar target/yudao-server.jar --version
```

## 🚀 本地测试

### 1. 启动数据库服务
```bash
# 启动MySQL
sudo systemctl start mysql

# 启动Redis
sudo systemctl start redis
```

### 2. 运行应用
```bash
# 方式1：直接运行jar包
java -jar target/yudao-server.jar

# 方式2：指定配置文件
java -jar target/yudao-server.jar --spring.profiles.active=prod

# 方式3：指定JVM参数
java -Xms512m -Xmx1024m -jar target/yudao-server.jar
```

### 3. 验证服务
```bash
# 检查服务是否启动
curl http://localhost:48080/actuator/health

# 或者访问Swagger文档
# http://localhost:48080/doc.html
```

## 📁 打包输出

### 文件位置
- **主jar包**: `target/yudao-server.jar`
- **依赖jar包**: `target/lib/` (如果使用Spring Boot插件)
- **配置文件**: 已打包在jar内

### 文件大小
- 通常生成的jar包大小在 50-100MB 之间
- 包含所有依赖和资源文件

## 🔍 常见问题

### 1. 编译失败
```bash
# 检查Maven版本
mvn -version

# 清理Maven缓存
mvn dependency:purge-local-repository

# 重新下载依赖
mvn clean install -U
```

### 2. 数据库连接失败
- 检查数据库服务是否启动
- 验证数据库连接配置
- 确认数据库用户权限

### 3. 端口占用
```bash
# 查看端口占用
netstat -tlnp | grep 48080

# 杀死占用进程
kill -9 <PID>
```

### 4. 内存不足
```bash
# 增加Maven内存
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"

# 或者修改pom.xml中的maven-compiler-plugin配置
```

## 📋 生产环境优化

### 1. JVM参数优化
```bash
# 生产环境启动命令
java -server \
  -Xms1g -Xmx2g \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/opt/logs/ \
  -jar yudao-server.jar \
  --spring.profiles.active=prod
```

### 2. 日志配置
```yaml
# application-prod.yaml
logging:
  level:
    root: INFO
    cn.iocoder.yudao: DEBUG
  file:
    name: /opt/logs/yudao-server.log
  pattern:
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n"
```

### 3. 监控配置
```yaml
# 启用监控端点
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

## ✅ 打包检查清单

- [ ] 数据库配置正确
- [ ] Redis配置正确
- [ ] 端口配置正确
- [ ] 日志配置正确
- [ ] 打包成功无错误
- [ ] 本地测试通过
- [ ] 健康检查正常
- [ ] API接口可访问

---

**注意**: 打包前请确保所有配置都已正确设置，并在测试环境中验证功能正常。
