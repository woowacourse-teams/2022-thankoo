#!/bin/bash
if [ $# -ne 2 ]; then
        echo “please insert property environment like -e prod | --environment dev)”
        exit 1
fi
if [ “$1" != “-e” ] && [ “$1" != “--environment” ]; then
        echo “this argument not allowed : $1"
        exit 1
fi
ENVIRONMENT=$2
PID=$(pgrep -f thankoo)
function killProcess() {
        echo “> 현재 프로세스 종료 작업 시작”
        if [ -z $PID ]; then
                echo “종료할 Process가 없습니다.”
        else
                kill -9 $PID
                echo “$PID 프로세스 종료”
        fi
}
function startJar() {
        echo “> 배포 시작”
        nohup java -jar -Duser.timezone=“Asia/Seoul” -Dspring.profiles.active=$ENVIRONMENT thankoo-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/thankoo-backend-secret/application-$ENVIRONMENT.yml 1>thankoo.log 2>thankoo-error.log &
}
killProcess
startJar
exit 0
