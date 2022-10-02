#!/bin/bash

if [ $# -eq 0 ]; then
    echo " > IP와 Port 번호를 입력하세요"
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
        echo " > ip를 입력해주세요."
        exit 1
fi

if [ -z "$PORT" ]; then
        echo " > 포트를 입력해주세요."
        exit 1
fi

FILE_LINE=$(tail -n 2 /etc/nginx/conf.d/service-url.inc | wc -l)

if [ "$FILE_LINE" -eq 1 ]
then
    echo " > 추가할 IP/PORT: $IP:$PORT"
    echo " > IP/PORT 추가"
    echo "set \$service_url_B ${IP}:${PORT};" | sudo tee -a /etc/nginx/conf.d/service-url.inc
else
    echo " > 수정할 IP/PORT: $IP:$PORT"
    echo " > IP/PORT 추가"
    echo "set \$service_url_A ${IP}:${PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
fi

echo " > 작성 대기"
sleep 3

echo " > Nginx 재시작"
sudo systemctl restart nginx

exit 0;
