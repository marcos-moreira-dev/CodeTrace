REM CodeTrace | Referencia: Windows 10/11 CMD
REM Comprobar: cmd /c 03-algoritmos/conteo/conteo.cmd
REM Ejecutar: cmd /c 03-algoritmos/conteo/conteo.cmd

@echo off
setlocal EnableExtensions EnableDelayedExpansion
REM Cliente de conteo iterativo y acumulación con FOR.
REM CMD resulta útil para automatizar tareas del sistema, no para
REM implementar complejos árboles o heaps. Aquí la cuenta recorre
REM secuencialmente los elementos: costo O(n) en iteraciones.
set "total=0"
for %%I in (3 5 8 13) do (
    set /a total+=%%I >nul
)
echo Suma: !total!
endlocal
