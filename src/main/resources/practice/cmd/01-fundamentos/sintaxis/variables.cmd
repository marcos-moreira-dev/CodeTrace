@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
@REM Verificar/compilar: cmd /c '.\01-fundamentos/sintaxis\variables.cmd'

@echo off
setlocal
set "nombre=CodeTrace"
set /a cantidad=2+3
echo %nombre%: %cantidad%
endlocal
