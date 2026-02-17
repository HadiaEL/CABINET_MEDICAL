@echo off
echo ========================================
echo Cabinet Medical - Fix Maven Dependencies
echo ========================================
echo.

echo [1/4] Nettoyage du cache Maven local...
call mvn dependency:purge-local-repository -DreResolve=false
echo.

echo [2/4] Nettoyage du projet...
call mvn clean
echo.

echo [3/4] Telechargement des dependances...
call mvn dependency:resolve
echo.

echo [4/4] Compilation du projet...
call mvn compile
echo.

if %ERRORLEVEL% EQU 0 (
    echo ========================================
    echo SUCCESS! Les dependances sont OK
    echo ========================================
    echo.
    echo Prochaines etapes:
    echo 1. Dans IntelliJ IDEA, faites: File ^> Invalidate Caches ^> Invalidate and Restart
    echo 2. Ou fermez et rouvrez le projet
    echo.
) else (
    echo ========================================
    echo ERREUR! Verifiez les logs ci-dessus
    echo ========================================
)

pause

