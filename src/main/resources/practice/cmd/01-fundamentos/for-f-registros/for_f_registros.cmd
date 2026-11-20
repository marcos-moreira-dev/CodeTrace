REM CodeTrace | Referencia: Windows 10/11 CMD
REM Comprobar: cmd /c ".\01-fundamentos\for-f-registros\for_f_registros.cmd"
REM Ejecutar: cmd /c ".\01-fundamentos\for-f-registros\for_f_registros.cmd"

@echo off
setlocal EnableExtensions EnableDelayedExpansion
REM Cliente de FOR /F: separar un registro delimitado por coma.
REM %%A y %%B reciben los campos; la expansión retardada permite
REM leer variables actualizadas dentro de bloques entre paréntesis.
REM Para archivos reales se necesita manejar quoting y caracteres especiales.
set "registro=Sensor,42"
for /f "tokens=1,2 delims=," %%A in ("!registro!") do (
    echo Componente: %%A
    echo Lectura: %%B
)
endlocal
