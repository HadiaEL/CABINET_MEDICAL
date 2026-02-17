@echo off
echo ====================================
echo Cabinet Medical - Build Script
echo ====================================
echo.

echo Checking Java version...
java -version
echo.

echo Checking Maven...
where mvn
echo.

echo Building project...
call mvn clean install -DskipTests
echo.

if %ERRORLEVEL% EQU 0 (
    echo ====================================
    echo Build SUCCESS!
    echo ====================================
) else (
    echo ====================================
    echo Build FAILED!
    echo ====================================
)

pause

