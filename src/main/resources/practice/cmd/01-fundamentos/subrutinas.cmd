REM CodeTrace | Referencia: Windows 10/11 CMD
REM Comprobar: cmd /c ARCHIVO.cmd
REM Ejecutar: cmd /c ARCHIVO.cmd

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
