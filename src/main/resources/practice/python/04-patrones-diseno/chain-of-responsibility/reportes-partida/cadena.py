# CodeTrace | Referencia: Python 3.12
# Comprobar: python -c "import ast,pathlib; ast.parse(pathlib.Path('04-patrones-diseno/chain-of-responsibility/reportes-partida/cadena.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B 04-patrones-diseno/chain-of-responsibility/reportes-partida/main.py

"""Patrón: Chain of Responsibility.
Dominio: reportes ficticios de una ronda táctica por equipos.
Cada especialista atiende su reporte o lo delega al siguiente.
Objetivo: separar al emisor de quien resuelve cada evento.
"""
from __future__ import annotations

from dataclasses import dataclass
from enum import Enum, auto


class TipoEvento(Enum):
    OBJETIVO = auto()
    ENEMIGO = auto()
    MUNICION = auto()
    OTRO = auto()


@dataclass(frozen=True)
class EventoPartida:
    tipo: TipoEvento
    detalle: str


class ManejadorEvento:
    """Eslabón base: la primera coincidencia termina la cadena."""

    def __init__(self) -> None:
        self.siguiente: ManejadorEvento | None = None

    def enlazar(self, siguiente: ManejadorEvento) -> ManejadorEvento:
        self.siguiente = siguiente
        return siguiente

    def atender(self, evento: EventoPartida) -> str:
        if self.siguiente is None:
            return f"Sin responsable: {evento.detalle}"
        return self.siguiente.atender(evento)


class ManejadorObjetivo(ManejadorEvento):
    def atender(self, evento: EventoPartida) -> str:
        if evento.tipo is TipoEvento.OBJETIVO:
            return f"Objetivo: {evento.detalle}"
        return super().atender(evento)


class ManejadorReconocimiento(ManejadorEvento):
    def atender(self, evento: EventoPartida) -> str:
        if evento.tipo is TipoEvento.ENEMIGO:
            return f"Reconocimiento: {evento.detalle}"
        return super().atender(evento)


class ManejadorEquipamiento(ManejadorEvento):
    def atender(self, evento: EventoPartida) -> str:
        if evento.tipo is TipoEvento.MUNICION:
            return f"Apoyo: {evento.detalle}"
        return super().atender(evento)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
#
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\chain-of-responsibility\reportes-partida\main.py
# Salida esperada:
#   Objetivo: asegurar el sitio B
#   Reconocimiento: enemigo en pasillo central
#   Apoyo: solicitar munición adicional
#   Sin responsable: señal desconocida
# CodeTrace | FIN EJEMPLO DE CONSOLA
