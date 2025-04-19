# 🐳 Spring Boot Microservices with Docker, MySQL & Logging

This project consists of two Spring Boot microservices:

1. **greeting** – A simple microservice that exposes a `/greet` endpoint and sends a log to the logging service.
2. **logging** – A microservice that connects to a MySQL database and stores logs.

Both services are containerized using Docker and coordinated with `docker-compose`. A `wait-for-mysql.sh` script ensures the logging service only starts after MySQL is fully ready.

---

## 🔧 Project Structure

```text
springboot-docker-logging-demo/
├── docker-compose.yml
├── greeting/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── com/example/greeting/
│                   ├── GreetingApplication.java
│                   ├── config/RestTemplateConfig.java
│                   └── controller/GreetingController.java
├── logging/
│   ├── Dockerfile
│   ├── wait-for-mysql.sh
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── com/example/logging/
│                   ├── LoggingApplication.java
│                   ├── controller/LogController.java
│                   ├── entity/LogEntry.java
│                   └── repository/LogEntryRepository.java
## 🐳 Docker Compose Configuration
docker-compose.yml
yaml


version: '3.8'

services:
  mysql:
    image: mysql:8
    container_name: log-mysql
    environment:
      MYSQL_ROOT_PASSWORD: alterego
      MYSQL_DATABASE: logdb
    ports:
      - "3307:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  logging:
    build:
      context: ./logging
    container_name: logging
    environment:
      MYSQL_URL: jdbc:mysql://mysql:3306/logdb
      MYSQL_USER: root
      MYSQL_PASSWORD: alterego
    depends_on:
      - mysql

  greeting:
    build:
      context: ./greeting
    container_name: greeting
    ports:
      - "8080:8080"
    environment:
      LOGGING_SERVICE_URL: http://logging:8081
    depends_on:
      - logging

volumes:
  mysql-data:
⚙️ wait-for-mysql.sh (Logging Service Startup Delay)
bash


#!/bin/sh

MYSQL_HOST=${MYSQL_HOST:-mysql}
MYSQL_PORT=${MYSQL_PORT:-3306}
MYSQL_USER=${MYSQL_USER:-root}
MYSQL_PASSWORD=${MYSQL_PASSWORD:-alterego}

echo "Waiting for MySQL to be ready on $MYSQL_HOST:$MYSQL_PORT as $MYSQL_USER..."

until mysql -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "SELECT 1;" >/dev/null 2>&1; do
  echo "MySQL is unavailable - sleeping"
  sleep 2
done

echo "✅ MySQL is ready - launching Spring Boot"
sleep 5
exec java -jar app.jar
🐘 Spring Boot MySQL Config (application.yml)
yaml


spring:
  datasource:
    url: ${MYSQL_URL:jdbc:mysql://mysql:3306/logdb}
    username: ${MYSQL_USER:root}
    password: ${MYSQL_PASSWORD:alterego}
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      initialization-fail-timeout: -1
      connection-timeout: 30000

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQLDialect
📦 Maven Dependency (pom.xml)
xml


<dependency>
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <version>8.0.33</version>
</dependency>
🛠️ Docker Commands Explained

## Command	Purpose
docker-compose up	Start containers using docker-compose config
docker-compose up --build	Force rebuild and start containers
docker-compose build --no-cache	Force a clean rebuild of all images
docker-compose down -v	Stop all containers and remove volumes (important for resetting MySQL)
docker exec -it <container> sh	Open a shell inside a running container
docker logs <container>	View logs from a running container
docker image prune -af	Clean up unused Docker images (safe if space is tight)
## 🧪 Testing Flow
Open: http://localhost:8080/greet

Greeting service sends a POST to Logging service.

Logging service writes the message to MySQL.

To verify:

bash
docker exec -it log-mysql mysql -uroot -palterego -e "USE logdb; SELECT * FROM log_entry;"
