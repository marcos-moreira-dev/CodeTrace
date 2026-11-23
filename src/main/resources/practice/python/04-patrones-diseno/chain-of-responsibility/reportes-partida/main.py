# CodeTrace | Referencia: Python 3.12
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
        EventoPartida(TipoEvento.OBJETIVO, "asegurar el sitio B"),
        EventoPartida(TipoEvento.ENEMIGO, "enemigo en pasillo central"),
        EventoPartida(TipoEvento.MUNICION, "solicitar munición adicional"),
        EventoPartida(TipoEvento.OTRO, "señal desconocida"),
    ]
    esperado = [
        "Objetivo: asegurar el sitio B",
        "Reconocimiento: enemigo en pasillo central",
        "Apoyo: solicitar munición adicional",
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
#   Objetivo: asegurar el sitio B
#   Reconocimiento: enemigo en pasillo central
#   Apoyo: solicitar munición adicional
#   Sin responsable: señal desconocida
# CodeTrace | FIN EJEMPLO DE CONSOLA
