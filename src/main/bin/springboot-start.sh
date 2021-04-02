#!/bin/sh
#对于JAVA语言的springboot应用 可以参考如下
# start.sh所在路径
SHDIR=$(cd "$(dirname "$0")"; pwd)
# 发布包路径
BASEDIR=$(cd $SHDIR/..; pwd)
cd $BASEDIR

APP_NAME="SURE中你的应用名"
# 应用启动日志路径一般建议用/export/log/
LOGDIR=/export/log/$APP_NAME
LOGFILE="$LOGDIR/${APP_NAME}_startup.log"
# 应用jar包和conf文件所在路径 最终也要包含在应用进程里，是获取进程的依据
CLASSPATH="$BASEDIR/conf/:$BASEDIR/lib/*"
# MAIN_MODULE根据不同应用自行配置
MAIN_MODULE="com.jd.icity.devops.alarm.PlatformAlarmApplication"

echo current path:$BASEDIR

# 创建应用日志目录
if [ ! -d "$LOGDIR" ] ;then
    mkdir "$LOGDIR"
    if [ $? -ne 0 ] ;then
        echo "Cannot create $LOGDIR" >&2
        exit 1
    fi
fi

# 获取进程信息 用的是 java + $CLASSPATH 路径 防止被服务器上其他进程干扰
function get_pid
{
    pgrep -lf "java .* $CLASSPATH"
}

[[ -z $(get_pid) ]] || {
    echo "ERROR:  $APP_NAME already running" >&2
    exit 1
}

echo "Starting $APP_NAME ...."

# 无需配置java路径 会自动使用应用在SURE上线全局配置里选的java版本
setsid java $SGM_OPTS -classpath "$CLASSPATH" -Dbasedir="$BASEDIR" -Dfile.encoding="UTF-8" $MAIN_MODULE > $LOGFILE 2>&1 &

# 如果应用启动较慢 根据实际情况考虑sleep时间
sleep 10

# 判断应用是否启动成功
[[ -n $(get_pid) ]] || {
    echo "ERROR: $APP_NAME failed to start" >&2
    exit 1
}

echo "$APP_NAME is up runnig :)"