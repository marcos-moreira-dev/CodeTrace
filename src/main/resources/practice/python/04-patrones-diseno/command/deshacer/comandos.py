# CodeTrace | Referencia: Python 3.12
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Editor del plan de equipo para una ronda del videojuego.
# Patrón command: Encapsular una edición y permitir deshacerla.
# Glosario: Ronda: segmento breve de la partida; deshacer: restaurar el estado anterior.
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('comandos.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Command encapsula una petición y su acción inversa.

Editor es el receptor de la modificación. El comando recuerda el
estado anterior y el cliente conserva un historial de comandos
en una pila. No son cadenas de órdenes del sistema operativo.
"""
from typing import Protocol

class Comando(Protocol):
    def ejecutar(self) -> None: ...
    def deshacer(self) -> None: ...

class Editor:
    def __init__(self) -> None: self.texto=""
    def agregar(self, parte: str) -> None: self.texto+=parte

class AgregarTexto:
    def __init__(self, editor: Editor, parte: str) -> None:
        self.editor=editor
        self.parte=parte
        self.anterior: str | None = None

    def ejecutar(self) -> None:
        if self.anterior is not None: raise ValueError("Ya ejecutado")
        self.anterior=self.editor.texto
        self.editor.agregar(self.parte)

    def deshacer(self) -> None:
        if self.anterior is None: raise ValueError("No ejecutado")
        self.editor.texto=self.anterior
        self.anterior=None

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\command\deshacer\main.py
# Nota: comandos.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Hola
# CodeTrace | FIN EJEMPLO DE CONSOLA
