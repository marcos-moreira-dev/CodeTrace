# CodeTrace | Referencia: Python 3.12
# Universo: Arena Táctica; reportes de ronda en partida ficticia.
# Glosario: rehén = personaje virtual a rescatar; MP9 = subfusil del juego.
# Minimapa: vista reducida del escenario; nada describe armas reales.
# Comprobar: python -m py_compile 04-patrones-diseno/chain-of-responsibility/reportes-partida/main.py
# Ejecutar: python -B 04-patrones-diseno/chain-of-responsibility/reportes-partida/main.py

"""Patrón: Chain of Responsibility.
Dominio: partida táctica ficticia; una ronda produce avisos de objetivos,
enemigos y equipamiento. Cada responsable procesa únicamente su categoría.
Objetivo: estudiar la delegación ordenada sin condicionales en el cliente.
"""
from cadena import (
    EventoPartida, ManejadorEquipamiento, ManejadorObjetivo,
    ManejadorReconocimiento, TipoEvento,
)


def main() -> None:
    primero = ManejadorObjetivo()
    primero.enlazar(ManejadorReconocimiento()).enlazar(ManejadorEquipamiento())
    eventos = [
        EventoPartida(TipoEvento.OBJETIVO, "rehén virtual rescatado"),
        EventoPartida(TipoEvento.ENEMIGO, "rival visto en el minimapa"),
        EventoPartida(TipoEvento.EQUIPAMIENTO, "MP9 registrado en inventario virtual"),
        EventoPartida(TipoEvento.OTRO, "señal desconocida"),
    ]
    esperado = [
        "Objetivo: rehén virtual rescatado",
        "Reconocimiento: rival visto en el minimapa",
        "Equipamiento: MP9 registrado en inventario virtual",
        "Sin responsable: señal desconocida",
    ]
    respuestas = [primero.atender(evento) for evento in eventos]
    assert respuestas == esperado, respuestas
    for respuesta in respuestas:
        print(respuesta)


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
#
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\chain-of-responsibility\reportes-partida\main.py
# Salida esperada:
#   Objetivo: rehén virtual rescatado
#   Reconocimiento: rival visto en el minimapa
#   Equipamiento: MP9 registrado en inventario virtual
#   Sin responsable: señal desconocida
# CodeTrace | FIN EJEMPLO DE CONSOLA
