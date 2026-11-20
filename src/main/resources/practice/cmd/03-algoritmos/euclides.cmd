@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM En una COPIA del paquete, desde PowerShell o shell indicado:
@REM Ejecutar: cmd /c '.\03-algoritmos\euclides.cmd'

@echo off
setlocal EnableDelayedExpansion
set /a a=48,b=18
:repetir
if !b! EQU 0 goto mostrar
set /a resto=a%%b
set /a a=b,b=resto
goto repetir
:mostrar
echo MCD: !a!
endlocal
