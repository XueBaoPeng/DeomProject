#!/bin/bash
APP_NAME="data-server-app"
PID=`/bin/ps -ef | grep java | grep $APP_NAME | grep -v grep |awk '{print $2}'`
if [ -n "$PID" ]; then
    echo "进程尝试关闭，进程号: $PID ..."
        kill -15 $PID
        sleep 3

        PID=`/bin/ps -ef | grep -v .sh | grep java | grep $APP_NAME | grep -v grep |awk '{print $2}'`
        if [ -n "$PID" ]; then
                kill -9 $PID
            echo "进程强制关闭!"
        else
            echo "进程关闭成功!"
        fi
    exit
else
    echo "进程已经关闭!"
    exit
fi
