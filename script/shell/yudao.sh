#!/bin/bash

# 应用配置
APP_NAME=yudao-server
APP_HOME=/work/projects/yudao-server
JAR_NAME=$APP_NAME.jar
JAR_PATH=$APP_HOME/$JAR_NAME

# JVM 参数
JVM_OPTS="-Xms512m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$APP_HOME/heapError"

# Spring 环境
SPRING_PROFILE=dev

# 日志文件
LOG_FILE=$APP_HOME/nohup.out

# 获取进程 PID
get_pid() {
    PID=$(ps -ef | grep $JAR_PATH | grep -v grep | awk '{print $2}')
    echo $PID
}

# 启动应用
start() {
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "应用已经在运行中，PID: $PID"
        return 1
    fi
    
    if [ ! -f "$JAR_PATH" ]; then
        echo "错误: JAR 包不存在: $JAR_PATH"
        return 1
    fi
    
    echo "正在启动 $APP_NAME ..."
    echo "JAR 路径: $JAR_PATH"
    echo "JVM 参数: $JVM_OPTS"
    echo "环境配置: $SPRING_PROFILE"
    echo "日志文件: $LOG_FILE"
    
    cd $APP_HOME
    nohup java $JVM_OPTS -jar $JAR_PATH --spring.profiles.active=$SPRING_PROFILE > $LOG_FILE 2>&1 &
    
    sleep 3
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "启动成功! PID: $PID"
        echo "查看日志: tail -f $LOG_FILE"
    else
        echo "启动失败，请查看日志: $LOG_FILE"
        return 1
    fi
}

# 停止应用
stop() {
    PID=$(get_pid)
    if [ -z "$PID" ]; then
        echo "应用未运行"
        return 0
    fi
    
    echo "正在停止 $APP_NAME (PID: $PID) ..."
    kill -15 $PID
    
    # 等待最多 30 秒
    for i in {1..30}; do
        sleep 1
        PID=$(get_pid)
        if [ -z "$PID" ]; then
            echo "停止成功"
            return 0
        fi
        echo -n "."
    done
    
    echo ""
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "优雅停止失败，强制停止..."
        kill -9 $PID
        sleep 2
        PID=$(get_pid)
        if [ -z "$PID" ]; then
            echo "强制停止成功"
        else
            echo "停止失败"
            return 1
        fi
    fi
}

# 重启应用
restart() {
    echo "正在重启 $APP_NAME ..."
    stop
    sleep 2
    start
}

# 查看状态
status() {
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "$APP_NAME 正在运行"
        echo "PID: $PID"
        echo "JAR: $JAR_PATH"
        ps -f -p $PID
    else
        echo "$APP_NAME 未运行"
    fi
}

# 查看日志
log() {
    if [ ! -f "$LOG_FILE" ]; then
        echo "日志文件不存在: $LOG_FILE"
        return 1
    fi
    tail -f $LOG_FILE
}

# 主函数
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    status)
        status
        ;;
    log)
        log
        ;;
    *)
        echo "用法: $0 {start|stop|restart|status|log}"
        echo ""
        echo "命令说明:"
        echo "  start   - 启动应用"
        echo "  stop    - 停止应用"
        echo "  restart - 重启应用"
        echo "  status  - 查看运行状态"
        echo "  log     - 查看实时日志"
        exit 1
        ;;
esac

exit 0
