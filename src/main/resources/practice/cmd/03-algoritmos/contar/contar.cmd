@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM En una COPIA del paquete, desde PowerShell o shell indicado:
@REM Ejecutar: cmd /c '.\03-algoritmos\contar\contar.cmd'

@echo off
for /L %%I in (1,1,5) do (
    echo Contador: %%I
)

REM CodeTrace | EJEMPLO DE CONSOLA (practica)
REM 
REM Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
REM   cmd /c .\03-algoritmos\contar\contar.cmd
REM Salida esperada:
REM   Contador: 1
REM   Contador: 2
REM   Contador: 3
REM   Contador: 4
REM   Contador: 5
REM CodeTrace | FIN EJEMPLO DE CONSOLA
