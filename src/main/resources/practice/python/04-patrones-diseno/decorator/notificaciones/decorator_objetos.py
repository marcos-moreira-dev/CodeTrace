# CodeTrace | Referencia: Python 3.12
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Mensajes que recibe un equipo durante una partida ficticia.
# Patrón decorator: Añadir presentación a una notificación sin cambiar el receptor.
# Glosario: Equipo: conjunto de jugadores; ronda: segmento breve de partida.
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('decorator_objetos.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Implementación del patrón Decorator sobre objetos.

GoF Decorator no es idéntico a la sintaxis @decorador aplicada a
funciones. Aquí se preserva el contrato enviar mediante Protocol
y un objeto envolvente que delega.
"""
from typing import Protocol


class Notificador(Protocol):
    def enviar(self, mensaje: str) -> None: ...


class Consola:
    def enviar(self, mensaje: str) -> None:
        print("Mensaje:", mensaje)


class ConPrefijo:
    def __init__(self, interno: Notificador, prefijo: str) -> None:
        self._interno = interno
        self._prefijo = prefijo

    def enviar(self, mensaje: str) -> None:
        self._interno.enviar(self._prefijo + mensaje)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\decorator\notificaciones\main.py
# Nota: decorator_objetos.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Mensaje: [INFO] Proceso terminado
# CodeTrace | FIN EJEMPLO DE CONSOLA
