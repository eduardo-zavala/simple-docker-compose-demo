FROM eclipse-temurin:17-jdk

# Install mysql-client
RUN apt-get update && \
    apt-get install -y mysql-client && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/logging*.jar app.jar
COPY wait-for-mysql.sh wait-for-mysql.sh
RUN chmod +x wait-for-mysql.sh
EXPOSE 8081
ENTRYPOINT ["./wait-for-mysql.sh"]