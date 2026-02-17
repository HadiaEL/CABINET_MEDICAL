@echo off
echo ====================================
echo Diagnostic Maven - Cabinet Medical
echo ====================================
echo.

echo [1] Verification Java...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java n'est pas installe ou pas dans le PATH
    pause
    exit /b 1
)
echo [OK] Java est installe
echo.

echo [2] Verification Maven...
mvn -version
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven n'est pas installe ou pas dans le PATH
    pause
    exit /b 1
)
echo [OK] Maven est installe
echo.

echo [3] Verification du pom.xml...
if exist "pom.xml" (
    echo [OK] pom.xml trouve
) else (
    echo [ERROR] pom.xml introuvable !
    pause
    exit /b 1
)
echo.

echo [4] Verification de la structure...
if exist "src\main\java\com\cabinetmedical\CabinetMedicalApplication.java" (
    echo [OK] Classe principale trouvee
) else (
    echo [ERROR] Classe principale introuvable !
    pause
    exit /b 1
)
echo.

echo [5] Test de resolution des dependances...
echo Cette etape peut prendre quelques minutes...
call mvn dependency:resolve -q
if %ERRORLEVEL% EQU 0 (
    echo [OK] Dependances resolues
) else (
    echo [WARNING] Probleme lors de la resolution des dependances
)
echo.

echo [6] Test de compilation...
echo Cette etape peut prendre quelques minutes...
call mvn clean compile -q
if %ERRORLEVEL% EQU 0 (
    echo [OK] Compilation reussie !
    echo.
    echo ====================================
    echo DIAGNOSTIC: TOUT EST OK !
    echo ====================================
    echo.
    echo Le probleme vient probablement de l'IDE.
    echo.
    echo Solutions:
    echo 1. Dans IntelliJ: Maven panel ^> Reload All Maven Projects
    echo 2. File ^> Invalidate Caches ^> Invalidate and Restart
    echo 3. Rouvrir le projet
    echo.
) else (
    echo [ERROR] Echec de la compilation
    echo.
    echo ====================================
    echo DIAGNOSTIC: PROBLEME MAVEN DETECTE
    echo ====================================
    echo.
    echo Executez: mvn clean compile
    echo Pour voir les details de l'erreur
    echo.
)

pause

