# 🐳 Spring Boot Microservices with Docker, MySQL & Logging

This project consists of two Spring Boot microservices:

- **greeting** – A simple microservice that exposes a `/greet` endpoint and sends log messages.
- **logging** – A microservice that connects to a MySQL database and stores logs.

Everything is containerized using Docker and coordinated with Docker Compose. A `wait-for-mysql.sh` script ensures the logging service only starts after MySQL is fully ready.

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
```


## 🚀 How to Run

```bash
# 1. Clone the project
git clone https://github.com/your-username/springboot-docker-logging-demo.git
cd springboot-docker-logging-demo

# 2. Build the JARs
cd greeting && mvn clean package -DskipTests && cd ..
cd logging && mvn clean package -DskipTests && cd ..

# 3. Run with Docker Compose
docker-compose up --build
```

### 📫 Access the Greeting Service

Open your browser and visit:

```
http://localhost:8080/greet?name=YourName
```

### 🗃️ Check the Logs Stored in MySQL

```bash
docker exec -it log-mysql mysql -uroot -palterego -e "USE logdb; SELECT * FROM log_entry;"
```

---

## 🛠️ Docker Commands

| Command                            | Description                                                   |
|-----------------------------------|---------------------------------------------------------------|
| `docker-compose up`               | Start all containers                                          |
| `docker-compose up --build`       | Rebuild and start containers                                  |
| `docker-compose build --no-cache` | Force a clean rebuild of all Docker images                    |
| `docker-compose down -v`          | Stop and remove all containers **and volumes**                |
| `docker exec -it <container> sh`  | Open a shell inside a running container                       |
| `docker logs <container>`         | View the logs of a specific container                         |
| `docker image prune -af`          | Remove all unused Docker images (clean up disk space)         |

---

## 🎉 What You’ve Learned

✅ Multi-container Docker setup with Spring Boot  
✅ Environment variable handling using `docker-compose.yml`  
✅ Database readiness handling with a custom script  
✅ Debugging ports, environment, and connection issues  
✅ Clean configuration separation (local vs Docker)

---

Happy coding! 💻🐳  
Feel free to star the repo if it helped you ⭐

