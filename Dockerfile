FROM openjdk:18-jdk-slim
LABEL maintainer=oimaoyanio
# 设置环境变量
ENV MYSQL_USERAME root
ENV MYSQL_PASSOWRD ar352878987
ENV REDIS_PASSWORD ar352878987
ENV RABBITMQ_USERNAME guest
ENV RABBITMQ_PASSWORD guest
# 复制到Docker Linux中的根目录下的QuickDevelop.jar
COPY target/*.jar /QuickDevelop.jar
ENTRYPOINT ["java","-jar","/QuickDevelop.jar"]