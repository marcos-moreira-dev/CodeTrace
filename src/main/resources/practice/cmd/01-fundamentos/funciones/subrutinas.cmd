@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM En una COPIA del paquete, desde PowerShell o shell indicado:
@REM Ejecutar: cmd /c '.\03-funciones\subrutinas.cmd'

@echo off
call :saludar "CodeTrace"
goto :eof
:saludar
echo Hola, %~1
goto :eof
