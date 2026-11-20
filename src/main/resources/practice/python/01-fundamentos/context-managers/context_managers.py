# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\01-fundamentos\context-managers\context_managers.py"
# Ejecutar: python -B ".\01-fundamentos\context-managers\context_managers.py"

"""Cliente de with y context managers para recursos temporales.

El contexto posee una fase de entrada y otra de salida. El recurso se
libera incluso cuando el código interno falla. Este ejemplo usa
TemporaryDirectory, que crea y elimina su propia carpeta sin alterar
la biblioteca de referencias de CodeTrace.
"""
from pathlib import Path
from tempfile import TemporaryDirectory


def main() -> None:
    with TemporaryDirectory(prefix="codetrace_") as temporal:
        destino = Path(temporal) / "datos.txt"
        destino.write_text("Ejemplo sin archivos persistentes", encoding="utf-8")
        print(destino.read_text(encoding="utf-8"))
    print("El contexto eliminó la carpeta temporal")


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\01-fundamentos\context-managers\context_managers.py
# Salida esperada:
#   Ejemplo sin archivos persistentes
#   El contexto eliminó la carpeta temporal
# CodeTrace | FIN EJEMPLO DE CONSOLA
