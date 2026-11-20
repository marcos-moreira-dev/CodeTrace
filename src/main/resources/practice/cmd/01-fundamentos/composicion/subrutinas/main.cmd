REM CodeTrace | Referencia: Windows CMD, subrutinas en dos archivos
REM Compilar: cmd /c 01-fundamentos/composicion/subrutinas/main.cmd
@echo off
setlocal
call "%~dp0funciones.cmd" :mensaje "Factura 001"
if errorlevel 1 echo No se pudo ejecutar la subrutina
endlocal

REM CodeTrace | EJEMPLO DE CONSOLA (practica)
REM 
REM Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
REM   cmd /c .\01-fundamentos\composicion\subrutinas\main.cmd
REM Salida esperada:
REM   Evento: Factura 001
REM CodeTrace | FIN EJEMPLO DE CONSOLA
