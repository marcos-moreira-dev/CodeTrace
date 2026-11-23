# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py
# Dominio: Counter-Strike — partida automática de rescate de NPC.
# Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

from adapter.ping import AdaptadorPing, TelemetriaLegada
from builder.sala import Configurador
from chain_of_responsibility.cadena import Aviso, crear_cadena
from composite.mapas import Carpeta, Mapa
from factory_method.reportes import Texto
from observer.eventos import MarcadorObservable
from strategy.economia import Economia, eco, equipo
from infraestructura.modelo import Jugador, ObjetoVirtual, Partida, Rehen

class Controlador:
    def __init__(self, vista):
        self.vista = vista

    def simular(self):
        sala = Configurador().mapa("cs_office").rondas(3).rescate(True).construir()
        astra = Jugador("Astra", "CT")
        # Las listas guardan referencias a los personajes virtuales;
        # no duplicamos a Astra cuando la tienda cambia sus créditos.
        partida = Partida(sala.mapa, [astra, Jugador("Nilo", "CT")],
            [Jugador("Rook", "T"), Jugador("Vega", "T")],
            [Rehen("Elena", "Archivo", 92, 38), Rehen("Omar", "Sala de juntas", 86, 24)])
        recursos = Carpeta("maps", [Carpeta("oficiales", [Mapa("cs_office")]),
                                   Mapa("de_dust2")])
        self.vista.presentar(partida, sala.rondas,
            AdaptadorPing(TelemetriaLegada()).mostrar(), recursos.contar())
        cadena = crear_cadena()
        observable = MarcadorObservable()
        observable.suscribir(self.vista.marcador)
        economia = Economia(eco)
        # Secuencia de ejemplo equivalente a Random(42) en Java.
        # La fijamos para comparar lenguajes sin depender de su RNG.
        desenlaces = [True, False, True]
        indice_rehen = 0
        for numero, rescate in enumerate(desenlaces, 1):
            partida.iniciar_ronda()
            print(f"--- RONDA {numero} ---")
            economia.politica = eco if numero % 2 else equipo
            print(economia.plan(astra.creditos))
            if numero % 2 == 0 and astra.comprar(ObjetoVirtual("MP9", "subfusil", 950)):
                print(cadena.atender(Aviso("equipo", "MP9 registrado como objeto virtual")))
            if rescate:
                # Elegimos una ficha existente, luego modificamos su estado.
                # El siguiente rescate utiliza el otro NPC de la lista.
                rehen = partida.rehenes[indice_rehen % len(partida.rehenes)]
                indice_rehen += 1
                rehen.rescatar(astra)
                partida.reloj = 60
                print(cadena.atender(Aviso("rehen", f"{rehen.nombre} llegó a la zona de extracción")))
                self.vista.ficha(rehen)
                partida.registrar("CT", f"rescate de {rehen.nombre}")
            else:
                partida.reloj = 0
                print("Tiempo agotado sin rescate")
                partida.registrar("T", "objetivo sin completar")
            # El saldo de la próxima ronda incluye la recompensa ilustrativa.
            astra.creditos += 300
            observable.anotar("CT", partida.marcador.ct)
            observable.anotar("T", partida.marcador.t)
        self.vista.finalizar(partida, Texto().publicar(partida.marcador.ct))
        return partida

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
