FROM openjdk:18-jdk-slim
LABEL maintainer=oimaoyanio
# 复制到Docker Linux中的根目录下的QuickDevelop.jar
COPY target/*.jar /QuickDevelop.jar
ENTRYPOINT ["java","-jar","/QuickDevelop.jar"]