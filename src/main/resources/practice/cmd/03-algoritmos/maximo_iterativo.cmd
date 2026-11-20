REM CodeTrace | Referencia: Windows 10/11 CMD
REM Comprobar: cmd /c ".\03-algoritmos\maximo_iterativo.cmd"
REM Ejecutar: cmd /c ".\03-algoritmos\maximo_iterativo.cmd"

@echo off
setlocal EnableExtensions EnableDelayedExpansion
REM Cliente de un barrido secuencial para encontrar el máximo.
REM Se mantienen un candidato y una invariante: tras procesar k
REM datos, !maximo! es el mayor de los k leídos. O(n) iteraciones,
REM memoria O(1), sin estructuras inventadas para un archivo batch.
set /a maximo=-2147483647 >nul
for %%I in (8 12 3 25 10) do (
    if %%I GTR !maximo! set /a maximo=%%I >nul
)
echo Maximo: !maximo!
endlocal
