# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Cliente de lista enlazada con tipos genéricos y dataclass.

Una inserción al principio no mueve otros elementos en memoria,
pero recorrer todos los nodos cuesta O(n). Se usa el mismo escenario
que las listas simples en Java, Kotlin y TypeScript; el código Python
aprovecha dataclass y typing.Generic en vez de replicar Java.
"""
from lista import ListaSimple


def main() -> None:
    materias = ListaSimple[str]()
    for materia in ("Matemática", "Estructuras", "Redes"):
        materias.insertar_inicio(materia)
    assert materias.valores() == ["Redes", "Estructuras", "Matemática"]
    print(materias.valores())


if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
