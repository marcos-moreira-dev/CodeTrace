@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
@REM Verificar/compilar: cmd /c '.\01-fundamentos/control\condiciones.cmd'

@echo off
setlocal
set /a numero=8
if %numero% GTR 5 (
    echo Mayor que cinco
) else (
    echo Cinco o menos
)
endlocal
