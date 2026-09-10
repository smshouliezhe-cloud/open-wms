@echo off
setlocal
cd /d %~dp0
if not exist runtime\bin\java.exe (
  echo [ERROR] Embedded Java runtime is missing.
  pause
  exit /b 1
)
if not exist waterworks-standalone.jar (
  echo [ERROR] waterworks-standalone.jar is missing.
  pause
  exit /b 1
)
start "" cmd /c "timeout /t 3 /nobreak >nul & start http://127.0.0.1:7861/"
runtime\bin\java.exe -jar waterworks-standalone.jar
pause
