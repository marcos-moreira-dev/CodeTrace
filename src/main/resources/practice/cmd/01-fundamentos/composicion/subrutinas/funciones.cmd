REM CodeTrace | Referencia: Windows CMD, subrutinas en dos archivos
REM Compilar: cmd /c 01-fundamentos/composicion/subrutinas/main.cmd
@echo off
if /I "%~1"==":mensaje" (
    echo Evento: %~2
    exit /b 0
)
echo Subrutina desconocida
exit /b 1
