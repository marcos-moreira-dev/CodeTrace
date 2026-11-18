@REM CodeTrace | Referencia: Windows CMD de Windows 10/11.
@REM En una COPIA del paquete, desde PowerShell o shell indicado:
@REM Ejecutar: cmd /c '.\05-algoritmos\contar.cmd'

@echo off
for /L %%I in (1,1,5) do (
    echo Contador: %%I
)
