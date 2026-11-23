# CodeTrace | Referencia: Python 3.12
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Árbol de mapas y recursos descargables del videojuego.
# Patrón composite: Tratar archivos individuales y carpetas completas de forma uniforme.
# Glosario: Mapa: escenario virtual donde sucede una partida.
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('elementos.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Composite: modelar uniformemente archivos y carpetas anidados.

Ambos implementan bytes() gracias a un Protocol. Costo del cálculo
O(n nodos); en un árbol grande podría almacenarse y actualizarse
caché si cambiara la frecuencia de consultas.
"""
from dataclasses import dataclass, field
from typing import Protocol

class Elemento(Protocol):
    def bytes(self) -> int: ...

@dataclass(frozen=True)
class Archivo:
    nombre: str
    tamanio: int
    def bytes(self) -> int:
        if self.tamanio < 0: raise ValueError("Tamaño")
        return self.tamanio

@dataclass
class Carpeta:
    nombre: str
    hijos: list[Elemento] = field(default_factory=list)

    def agregar(self, elemento: Elemento) -> None:
        if elemento is self: raise ValueError("Ciclo directo")
        self.hijos.append(elemento)

    def bytes(self) -> int:
        return sum(h.bytes() for h in self.hijos)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\composite\arbol-archivos\main.py
# Nota: elementos.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Bytes: 220
# CodeTrace | FIN EJEMPLO DE CONSOLA
