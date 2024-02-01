#!/usr/bin/env bash

echo "> 현재 실행 중인 Docker 컨테이너 pid 확인" >> /home/ubuntu/ranger
CURRENT_PID=$(sudo docker container ls -q)
REPOSITORY=/home/ubuntu/ranger

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 Docker 컨테이너가 없으므로 종료하지 않습니다." >> /home/ubuntu/ranger
else
  echo "> sudo docker stop $CURRENT_PID"   # 현재 구동중인 Docker 컨테이너가 있다면 모두 중지
  sudo docker stop $CURRENT_PID
  sleep 5
fi

cd $REPOSITORY
sudo docker build -t ranger-api-spring-boot-docker .
sudo docker run -d -p 8080:8080 ranger-api-spring-boot-docker
