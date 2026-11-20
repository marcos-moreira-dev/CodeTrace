REM CodeTrace | Referencia: Windows 10/11 CMD
REM Comprobar: cmd /c 01-fundamentos/expansion-retardada/expansion_retardada.cmd
REM Ejecutar: cmd /c 01-fundamentos/expansion-retardada/expansion_retardada.cmd

@echo off
setlocal EnableExtensions EnableDelayedExpansion
REM Cliente de la diferencia entre %%VAR%% y !VAR!.
REM Dentro de un bloque entre paréntesis, %%contador%% puede expandirse
REM antes de que se ejecuten las órdenes. !contador! se evalúa en tiempo
REM de ejecución cuando está activada la expansión retardada.
set "contador=0"
for %%I in (1 2 3) do (
    set /a contador+=1 >nul
    echo Iteracion %%I: !contador!
)
endlocal
