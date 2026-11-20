# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Cliente Observer con callbacks de primera clase.

No es necesario imitar una interfaz Java en Python: una función
tiene identidad y puede suscribirse/desuscribirse del emisor.
La instantánea tuple evita que el orden de notificaciones cambie
si un observador se elimina durante la publicación.
"""
from observador import Emisor


def main() -> None:
    emisor = Emisor()
    historia: list[int] = []
    def registrar(valor: int) -> None:
        historia.append(valor)

    emisor.suscribir(registrar)
    emisor.actualizar(42)
    emisor.desuscribir(registrar)
    emisor.actualizar(99)
    assert historia == [42] and emisor.valor == 99
    print("Observer y desuscripción verificados")


if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Observer y desuscripción verificados
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
