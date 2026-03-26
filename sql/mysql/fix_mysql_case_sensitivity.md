# 解决 MySQL 表名大小写敏感问题

## 问题原因
- **Windows/macOS**: MySQL 默认不区分表名大小写
- **Linux (Ubuntu)**: MySQL 默认区分表名大小写
- Quartz 代码中使用大写表名 `QRTZ_TRIGGERS`，但数据库中是小写 `qrtz_triggers`

## 解决方案：设置 MySQL 不区分表名大小写

### 步骤1：修改 MySQL 配置文件

编辑 MySQL 配置文件（通常是 `/etc/mysql/mysql.conf.d/mysqld.cnf` 或 `/etc/my.cnf`）：

```bash
sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf
```

在 `[mysqld]` 部分添加：

```ini
[mysqld]
lower_case_table_names=1
```

### 步骤2：重启 MySQL 服务

```bash
sudo systemctl restart mysql
```

### 步骤3：验证设置

```bash
mysql -h 192.168.10.24 -u root -p123456 -e "SHOW VARIABLES LIKE 'lower_case_table_names';"
```

应该显示：
```
+------------------------+-------+
| Variable_name          | Value |
+------------------------+-------+
| lower_case_table_names | 1     |
+------------------------+-------+
```

### 步骤4：重启应用

```bash
cd /work/projects/yudao-server
./yudao-simple.sh restart
```

## 注意事项

⚠️ **重要**：`lower_case_table_names` 必须在初始化数据库之前设置。如果数据库已经存在：

1. **如果可以重新导入数据**（推荐）：
   ```bash
   # 备份数据
   mysqldump -h 192.168.10.24 -u root -p123456 ruoyi-vue-pro > backup.sql
   
   # 删除数据库
   mysql -h 192.168.10.24 -u root -p123456 -e "DROP DATABASE \`ruoyi-vue-pro\`;"
   
   # 修改配置后重启 MySQL
   sudo systemctl restart mysql
   
   # 重新创建数据库并导入
   mysql -h 192.168.10.24 -u root -p123456 -e "CREATE DATABASE \`ruoyi-vue-pro\` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
   mysql -h 192.168.10.24 -u root -p123456 ruoyi-vue-pro < backup.sql
   ```

2. **如果不能重新导入**（已有数据库）：
   - MySQL 8.0+ 可能不允许在已有数据库的情况下修改此参数
   - 只能使用前面的方案：删除小写表，保留大写表
   - 或者修改应用配置使用小写表名（不推荐）

## 快速临时方案（如果不想改 MySQL 配置）

直接删除小写的 Quartz 表，保留大写的：

```bash
mysql -h 192.168.10.24 -u root -p123456 ruoyi-vue-pro < sql/mysql/remove_lowercase_quartz_tables.sql
```

然后重启应用即可。

## 推荐方案

**生产环境推荐**：设置 `lower_case_table_names=1`，这样：
- 与 Windows 开发环境保持一致
- 避免大小写问题
- 更容易维护

**当前快速方案**：删除小写表，使用大写表（已经执行过了）
