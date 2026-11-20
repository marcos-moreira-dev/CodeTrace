REM CodeTrace | Referencia: Windows CMD, subrutinas en dos archivos
REM Compilar: cmd /c 01-fundamentos/composicion/subrutinas/main.cmd
@echo off
if /I "%~1"==":mensaje" (
    echo Evento: %~2
    exit /b 0
)
echo Subrutina desconocida
exit /b 1

REM CodeTrace | EJEMPLO DE CONSOLA (practica)
REM 
REM Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
REM   cmd /c .\01-fundamentos\composicion\subrutinas\main.cmd
REM Nota: funciones.cmd es un componente; la salida corresponde al cliente main.cmd.
REM Salida esperada:
REM   Evento: Factura 001
REM CodeTrace | FIN EJEMPLO DE CONSOLA
