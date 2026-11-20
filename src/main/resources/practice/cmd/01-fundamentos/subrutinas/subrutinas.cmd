REM CodeTrace | Referencia: Windows 10/11 CMD
REM Comprobar: cmd /c 01-fundamentos/subrutinas/subrutinas.cmd
REM Ejecutar: cmd /c 01-fundamentos/subrutinas/subrutinas.cmd

@echo off
setlocal EnableExtensions
REM Cliente de CALL :etiqueta y EXIT /B en un archivo batch.
REM Una etiqueta y una función de Java no son equivalentes: CALL crea
REM un regreso al flujo del lote. ERRORLEVEL informa el resultado.
call :doble 7
if errorlevel 1 exit /b 1
echo Resultado: %resultado%
exit /b 0
:doble
set /a resultado=%~1 * 2 >nul
exit /b 0

REM CodeTrace | EJEMPLO DE CONSOLA (practica)
REM 
REM Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
REM   cmd /c .\01-fundamentos\subrutinas\subrutinas.cmd
REM Salida esperada:
REM   Resultado: 14
REM CodeTrace | FIN EJEMPLO DE CONSOLA
