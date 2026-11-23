# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py
# Dominio: Counter-Strike — partida automática de rescate de NPC.
# Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

from dataclasses import dataclass, field
from enum import Enum, auto
from command.marcador import Marcador, SumarCt

class EstadoRehen(Enum):
    RETENIDO = auto()
    ESCOLTADO = auto()
    RESCATADO = auto()

@dataclass(frozen=True)
class ObjetoVirtual:
    nombre: str
    categoria: str
    precio: int

@dataclass
class Jugador:
    alias: str
    bando: str
    creditos: int = 1600
    vida: int = 100
    armadura: int = 35
    inventario: list[ObjetoVirtual] = field(default_factory=list)

    def comprar(self, objeto: ObjetoVirtual):
        # El objeto MP9 representa una ficha de inventario del videojuego.
        # Un saldo insuficiente no modifica la ficha ni la economía.
        if objeto.precio < 0 or self.creditos < objeto.precio:
            return False
        self.creditos -= objeto.precio
        self.inventario.append(objeto)
        return True

@dataclass
class Rehen:
    """NPC rescatable; ubicación de aparición y atributos narrativos ficticios."""
    nombre: str
    ubicacion: str
    integridad: int
    calma_inicial: int
    calma: int = field(init=False)
    progreso: int = 0
    escolta: str = ""
    estado: EstadoRehen = EstadoRehen.RETENIDO

    def __post_init__(self):
        self.reiniciar()

    def reiniciar(self):
        # En cada ronda el mismo NPC vuelve al objetivo inicial;
        # el historial de victorias de la partida permanece intacto.
        self.calma = self.calma_inicial
        self.progreso = 0
        self.escolta = ""
        self.estado = EstadoRehen.RETENIDO

    def rescatar(self, jugador: Jugador):
        if jugador.bando != "CT" or self.estado is not EstadoRehen.RETENIDO:
            raise ValueError("Escolta inválida")
        self.escolta = jugador.alias
        self.calma = min(100, self.calma + 12)
        self.estado = EstadoRehen.ESCOLTADO
        # El porcentaje es un estado abstracto, no una ruta espacial.
        self.progreso = 100
        self.estado = EstadoRehen.RESCATADO

@dataclass
class Partida:
    mapa: str
    ct: list[Jugador]
    t: list[Jugador]
    rehenes: list[Rehen]
    marcador: Marcador = field(default_factory=Marcador)
    historial: list[str] = field(default_factory=list)
    reloj: int = 115

    def iniciar_ronda(self):
        self.reloj = 115
        # Reiniciamos atributos de ronda; no perdemos créditos ni inventario.
        for jugador in self.ct + self.t:
            jugador.vida, jugador.armadura = 100, 35
        for rehen in self.rehenes:
            rehen.reiniciar()

    def registrar(self, ganador, motivo):
        if ganador == "CT":
            SumarCt(self.marcador).ejecutar()
        else:
            self.marcador.t += 1
        # Guardamos texto inmutable, no el NPC que se reiniciará.
        self.historial.append(f"{ganador}: {motivo}")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\05-patrones-diseno-counter-strike\main.py
# Salida esperada:
#   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
#   Mapa: cs_office | Rondas: 3 | Ping: 42 ms
#   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
#   Mapas registrados: 2
#   --- RONDA 1 ---
#   Eco: guardar 1600 créditos
#   Rescate: Elena llegó a la zona de extracción
#   Rehén Elena: RESCATADO | escolta=Astra | calma=50 | integridad=92
#   Marcador: CT 1 - 0 T
#   --- RONDA 2 ---
#   Equipo: asignar 1900 créditos virtuales
#   Inventario: MP9 registrado como objeto virtual
#   Tiempo agotado sin rescate
#   Marcador: CT 1 - 1 T
#   --- RONDA 3 ---
#   Eco: guardar 1250 créditos
#   Rescate: Omar llegó a la zona de extracción
#   Rehén Omar: RESCATADO | escolta=Astra | calma=36 | integridad=86
#   Marcador: CT 2 - 1 T
#   === FIN DE LA PARTIDA ===
#   Resultado: Gana CT
#   Créditos Astra: 1550
#   Reporte: CT=2
# CodeTrace | FIN EJEMPLO DE CONSOLA
