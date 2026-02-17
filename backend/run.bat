@echo off
echo ====================================
echo Cabinet Medical - Run Application
echo ====================================
echo.

echo Starting PostgreSQL with Docker Compose...
cd ..
docker-compose up -d
cd backend
echo.

echo Waiting for database to be ready...
timeout /t 5 /nobreak
echo.

echo Starting Spring Boot application...
call mvn spring-boot:run -Dspring-boot.run.profiles=dev

