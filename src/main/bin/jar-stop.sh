#!/bin/sh

# stop.sh所在路径
SHDIR=$(cd "$(dirname "$0")"; pwd)
# 发布包路径
BASEDIR=$(cd $SHDIR/..; pwd)
cd $BASEDIR

APP_NAME="SURE中你的应用名"
# 应用jar包必须要带路径信息 下面以jar文件明和应用名一致为例子
MAIN_JAR="$BASEDIR/$APP_NAME.jar"

function get_pid
{
    pgrep -lf "java .* $MAIN_JAR"
}

echo "Stopping $APP_NAME ...."

if ! get_pid; then
    echo "App not running, exit"
else
    pkill -9 -f "java .* $MAIN_JAR"
    sleep 5
fi

if get_pid; then
    echo "Stop $APP_NAME failed..."
    exit 1
fi