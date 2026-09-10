@echo off
setlocal
cd /d %~dp0
where java >nul 2>nul || (echo [ERROR] Java 17 is required.& pause & exit /b 1)
where mvn >nul 2>nul || (echo [ERROR] Maven is required for packaging.& pause & exit /b 1)
call mvn -DskipTests clean package
if errorlevel 1 (pause & exit /b 1)
echo.
echo Build complete: target\waterworks-standalone.jar
pause
