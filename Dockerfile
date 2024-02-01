# 17-jdk 이미지로 부터 시작
FROM openjdk:17-jdk

# 8080번 포트를 열어야한다는 명시
EXPOSE 8080

# 인자 정리
ARG JAR_FILE=build/libs/*.jar

# 앞에는 HOST OS의 현재 폴더를 의미한다.
# 뒤에는 컨테이너의 현재 폴더를 의미
COPY ${JAR_FILE} app.jar

# docker run 명령에서 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
