@echo off
echo ====================================
echo Cabinet Medical - Verification
echo ====================================
echo.

echo [1] Checking Java...
java -version 2>&1 | findstr "version"
if %ERRORLEVEL% EQU 0 (
    echo [OK] Java is installed
) else (
    echo [ERROR] Java is not installed
)
echo.

echo [2] Checking Maven...
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] Maven is installed
    mvn -version | findstr "Apache Maven"
) else (
    echo [WARNING] Maven is not found in PATH
)
echo.

echo [3] Checking Docker...
where docker >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] Docker is installed
    docker --version
) else (
    echo [WARNING] Docker is not found in PATH
)
echo.

echo [4] Checking project files...
if exist "pom.xml" (
    echo [OK] pom.xml found
) else (
    echo [ERROR] pom.xml not found
)

if exist "src\main\java\com\cabinetmedical\CabinetMedicalApplication.java" (
    echo [OK] Main application class found
) else (
    echo [ERROR] Main application class not found
)

if exist "..\docker-compose.yml" (
    echo [OK] docker-compose.yml found
) else (
    echo [ERROR] docker-compose.yml not found
)
echo.

echo ====================================
echo Verification complete!
echo ====================================
echo.
echo Next steps:
echo 1. Run: docker-compose up -d (from root)
echo 2. Run: mvn clean install
echo 3. Run: mvn spring-boot:run
echo.
pause

