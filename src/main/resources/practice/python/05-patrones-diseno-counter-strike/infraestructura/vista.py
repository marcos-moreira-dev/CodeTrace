# CodeTrace | Referencia: Python 3.12
# Ejecutar: python -B .\05-patrones-diseno-counter-strike\main.py
# Dominio: Counter-Strike — partida automática de rescate de NPC.
# Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

class VistaConsola:
    """La vista solo lee el estado; no otorga créditos ni decide ganadores."""
    def presentar(self, partida, rondas, ping, recursos):
        print("=== COUNTER-STRIKE | PARTIDA DIDÁCTICA ===")
        print(f"Mapa: {partida.mapa} | Rondas: {rondas} | {ping}")
        ct = ", ".join(j.alias for j in partida.ct)
        t = ", ".join(j.alias for j in partida.t)
        print(f"Equipos: CT ({ct}) vs T ({t})")
        print(f"Mapas registrados: {recursos}")

    def ficha(self, rehen):
        # La ficha observada es la misma instancia registrada en Partida.
        print(f"Rehén {rehen.nombre}: {rehen.estado.name} | escolta={rehen.escolta}"
              f" | calma={rehen.calma} | integridad={rehen.integridad}")

    def marcador(self, equipo, puntos):
        self.puntos[equipo] = puntos
        # El controlador publica CT y T consecutivamente; imprimimos al recibir T.
        if equipo == "T":
            print(f"Marcador: CT {self.puntos['CT']} - {puntos} T")

    def __init__(self):
        self.puntos = {"CT": 0, "T": 0}

    def finalizar(self, partida, reporte):
        print("=== FIN DE LA PARTIDA ===")
        m = partida.marcador
        resultado = "Gana CT" if m.ct > m.t else "Gana T" if m.t > m.ct else "Empate"
        print(f"Resultado: {resultado}")
        print(f"Créditos Astra: {partida.ct[0].creditos}")
        print(f"Reporte: {reporte}")

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
