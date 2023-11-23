#!/usr/bin/env bash

PROJECT_NAME=ranger
REPOSITORY=/home/ubuntu/ranger
cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

echo $JAR_NAME
echo $JAR_PATH

CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다"
else
  echo "> 실행 중인 애플리케이션 종료 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 배포 - $JAR_PATH"
nohup java -jar $JAR_PATH &
