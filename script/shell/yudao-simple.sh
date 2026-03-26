#!/bin/bash

# 配置
JAR_PATH=/work/projects/yudao-server/yudao-server.jar
LOG_FILE=/work/projects/yudao-server/nohup.out

# 获取进程ID
get_pid() {
    ps -ef | grep $JAR_PATH | grep -v grep | awk '{print $2}'
}

# 启动
start() {
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "应用已运行，PID: $PID"
        return
    fi
    
    echo "启动应用..."
    cd /work/projects/yudao-server
    nohup java -Xms512m -Xmx512m -jar $JAR_PATH > $LOG_FILE 2>&1 &
    sleep 3
    
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "启动成功! PID: $PID"
    else
        echo "启动失败，查看日志: tail -f $LOG_FILE"
    fi
}

# 停止
stop() {
    PID=$(get_pid)
    if [ -z "$PID" ]; then
        echo "应用未运行"
        return
    fi
    
    echo "停止应用 PID: $PID"
    kill -15 $PID
    
    # 等待30秒
    for i in {1..30}; do
        sleep 1
        PID=$(get_pid)
        if [ -z "$PID" ]; then
            echo "停止成功"
            return
        fi
    done
    
    # 强制停止
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "强制停止..."
        kill -9 $PID
        echo "已强制停止"
    fi
}

# 重启
restart() {
    stop
    sleep 2
    start
}

# 状态
status() {
    PID=$(get_pid)
    if [ -n "$PID" ]; then
        echo "应用运行中，PID: $PID"
    else
        echo "应用未运行"
    fi
}

# 日志
log() {
    tail -f $LOG_FILE
}

# 主程序
case "$1" in
    start)   start ;;
    stop)    stop ;;
    restart) restart ;;
    status)  status ;;
    log)     log ;;
    *)
        echo "用法: $0 {start|stop|restart|status|log}"
        exit 1
        ;;
esac
