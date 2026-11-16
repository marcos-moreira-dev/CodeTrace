@echo off
setlocal
cd /d "%~dp0\.."
set "PSEXE=%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe"
if not exist "%PSEXE%" (
  echo Windows PowerShell 5.1 es necesario.
  exit /b 2
)
"%PSEXE%" -NoProfile -ExecutionPolicy Bypass -File "%~dp0diagnostico.ps1" %*
set "RETURN_CODE=%ERRORLEVEL%"
endlocal & exit /b %RETURN_CODE%
