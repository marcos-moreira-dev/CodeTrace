@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM En una COPIA del paquete, desde PowerShell o shell indicado:
@REM Ejecutar: cmd /c '.\01-fundamentos/funciones\subrutinas.cmd'

@echo off
call :saludar "CodeTrace"
goto :eof
:saludar
echo Hola, %~1
goto :eof

REM CodeTrace | EJEMPLO DE CONSOLA (practica)
REM 
REM Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
REM   cmd /c .\01-fundamentos\funciones\subrutinas.cmd
REM Salida esperada:
REM   Hola, CodeTrace
REM CodeTrace | FIN EJEMPLO DE CONSOLA
