#!/bin/bash

echo "> Nginx 설정 시작"

if [ $# -eq 0 ]; then
    echo "> IP와 Port 번호를 입력하세요."
    exit 1
fi

IP=""
PORT=""

while (($#)); do
    case "$1" in
            -i|--ip)
                    if [ -n "$2" ]; then
                            IP=$2
                            shift 2
                    fi
                    ;;
            -p|--port)
                    if [ -n "$2" ]; then
                            PORT=$2
                            shift 2
                    fi
                    ;;
            -h|--help)
                    if [ $# -eq 1 ]; then
                            echo "help argument command"
                            echo "-i | --ip : public ip 주소"
                            echo "-p | --port : port 번호"
                            exit 0
                    else
                            echo "-h | --help 명령어만 입력하세요"
                            exit 1
                    fi
                    ;;

    esac
done

if [ -z "$IP" ]; then
        echo "> ip를 입력해주세요."
        exit 1
fi

if [ -z "$PORT" ]; then
        echo "> 포트를 입력해주세요."
        exit 1
fi

echo " > 수정할 IP/PORT: $IP:$PORT"
echo " > IP/PORT 추가"
echo "set \$service_url ${IP}:${PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

sudo systemctl reload nginx

echo "> Nginx 설정 종료"

exit 0;
