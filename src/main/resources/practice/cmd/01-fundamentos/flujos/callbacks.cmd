@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM En una COPIA del paquete, desde PowerShell o shell indicado:
@REM Ejecutar: cmd /c '.\01-fundamentos/flujos\callbacks.cmd'

@echo off
call :evento 42
goto :eof
:evento
echo Cambio: %~1
goto :eof

REM CodeTrace | EJEMPLO DE CONSOLA (practica)
REM 
REM Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
REM   cmd /c .\01-fundamentos\flujos\callbacks.cmd
REM Salida esperada:
REM   Cambio: 42
REM CodeTrace | FIN EJEMPLO DE CONSOLA
