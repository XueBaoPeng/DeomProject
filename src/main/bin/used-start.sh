#!/bin/bash
export CATALINA_BASE=/export/Logs/data-server-app
export JAVA_HOME=/export/servers/jdk1.8.0_66
export JAVA_BIN=/export/servers/jdk1.8.0_66/bin
export PATH=$JAVA_BIN:$PATH


nohup java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8110  /export/App/data-server-app/data-server-app.jar --spring.profiles.active=test --server.port=8082 > /dev/null 2>&1 &

echo Start Success!

