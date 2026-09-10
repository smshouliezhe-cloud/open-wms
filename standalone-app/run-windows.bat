@echo off
setlocal
cd /d %~dp0
if not exist target\waterworks-standalone.jar (
  echo [ERROR] target\waterworks-standalone.jar not found.
  echo Run package-windows.bat first.
  pause
  exit /b 1
)
start "Waterworks Standalone" http://127.0.0.1:7861/
java -jar target\waterworks-standalone.jar
pause
