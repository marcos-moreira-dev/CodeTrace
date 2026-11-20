# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente lista enlazada circular aplicada a turnos rotativos.

Si recorriéramos buscando None, el bucle sería infinito. Se mantiene
cantidad y se rota tail en O(1). Comparar con la lista lineal simple
y con una cola de capacidad fija para entender sus invariantes.
"""
from lista import ListaCircular

def main() -> None:
    turnos = ListaCircular[str]()
    assert turnos.valores() == []
    for x in ("Ana", "Luis", "Mia"):
        turnos.agregar(x)
    turnos.rotar()
    assert turnos.valores() == ["Luis", "Mia", "Ana"]
    print(turnos.valores())

if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
