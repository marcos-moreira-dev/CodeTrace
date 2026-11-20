# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Composite: tratar hoja y grupo con el mismo contrato.

No se necesita instanceof en el cliente: una Carpeta responde bytes()
igual que Archivo. Esta demo modela un árbol acíclico y solo valida
el ciclo directo, no ciclos indirectos en un grafo general.
"""
from elementos import Archivo, Carpeta

def main() -> None:
    raiz=Carpeta("proyecto")
    fuentes=Carpeta("src")
    fuentes.agregar(Archivo("Main.py",120))
    fuentes.agregar(Archivo("App.py",80))
    raiz.agregar(fuentes);raiz.agregar(Archivo("README.md",20))
    assert raiz.bytes()==220
    print("Bytes:",raiz.bytes())

if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Bytes:
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
