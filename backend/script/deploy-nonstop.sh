#!/bin/bash

if [ $# -ne 2 ]; then
    echo "please insert property environment like ( -e prod | --environment dev )"
    exit 1
fi

if [ "$1" != "-e" ] && [ "$1" != "--environment" ]; then
    echo "this argument not allowed : $1"
    exit 1
fi

ENVIRONMENT=$2

JAR_NAME=$(ls *.jar)
BLUE_PORT=""
GREEN_PORT=""
BLUE_PID=""
MAIN_PORT=$(curl -s http://localhost:8080/actuator/health)
SUB_PORT=$(curl -s http://localhost:8081/actuator/health)

function extractBlueGreenPort() {
    if [ -z "$MAIN_PORT" ] && [[ "$SUB_PORT" == *UP* ]]
    then
      BLUE_PORT=8081
      GREEN_PORT=8080
    elif [ -z "$SUB_PORT" ] && [[ "$MAIN_PORT" == *UP* ]]
    then
      BLUE_PORT=8080
      GREEN_PORT=8081
    else
      echo " > No Blue Port "
      GREEN_PORT=8080
    fi
    echo " > BLUE_PORT = $BLUE_PORT"
    echo " > GREEN_PORT = $GREEN_PORT"

    BLUE_PID=$(lsof -i:$BLUE_PORT | tail -n 1 | awk '{print $2}')
    echo " > BLUE_PID = $BLUE_PID"
}

function startGreen() {
    echo " > Start Green"
    nohup java -jar -Dserver.port="$GREEN_PORT" -Duser.timezone="Asia/Seoul" -Dspring.profiles.active="$ENVIRONMENT" "$JAR_NAME" --spring.config.location=classpath:/thankoo-backend-secret/application-"$ENVIRONMENT".yml > /dev/null 2>&1 &
    echo " > green 배포까지 Health Check"

    for RETRY_COUNT in {1..5}
    do
      RESPONSE=$(curl -s http://localhost:"$GREEN_PORT"/actuator/health)
      UP_COUNT=$(echo "$RESPONSE" | grep -c UP)

      if [ "$UP_COUNT" -ge 1 ]
      then
          echo "> Health check 성공"
          break
      else
          echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
          echo "> Health check: ${RESPONSE}"
      fi

      if [ "$RETRY_COUNT" -eq 5 ]
      then
        echo "> Health check 최종 실패. 배포 종료"
        exit 1
      fi

      echo "> Health check 연결 실패. 시도 횟수: $RETRY_COUNT / 5"
      sleep 10
    done
}

function setNginxPort() {
    echo " > nginx 포트 설정을 바꿀 sh 파일을 전송한다. "
}

function killBlue() {
    echo " > Kill Blue"
    if [ -z "$BLUE_PID" ]; then
                echo "종료할 Process가 없습니다."
          else
                kill -9 "$BLUE_PID"
                echo "$BLUE_PID 프로세스 종료"
          fi
}

extractBlueGreenPort
startGreen
setNginxPort
killBlue
