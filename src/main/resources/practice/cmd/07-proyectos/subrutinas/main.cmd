REM CodeTrace | Referencia: Windows CMD, subrutinas en dos archivos
REM Compilar: cmd /c 07-proyectos/subrutinas/main.cmd
@echo off
setlocal
call "%~dp0funciones.cmd" :mensaje "Factura 001"
if errorlevel 1 echo No se pudo ejecutar la subrutina
endlocal
