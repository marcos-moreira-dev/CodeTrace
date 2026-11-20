# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\03-algoritmos\seleccion-intervalos\seleccion_intervalos.py"
# Ejecutar: python -B ".\03-algoritmos\seleccion-intervalos\seleccion_intervalos.py"

"""Cliente de voraces: máximo número de intervalos compatibles.

Se ordenan por su tiempo de terminación y se toma cada intervalo
que empieza después de terminar el último aceptado. Este criterio
es óptimo para maximizar la CANTIDAD, no la suma de beneficios.
Tiempo O(n log n) por ordenación; O(n) para guardar la solución.
"""
from dataclasses import dataclass


@dataclass(frozen=True)
class Intervalo:
    inicio: int
    fin: int


def seleccionar(intervalos: list[Intervalo]) -> list[Intervalo]:
    if any(i.inicio >= i.fin for i in intervalos):
        raise ValueError("Intervalos vacíos")
    resultado: list[Intervalo] = []
    ultimo_fin = float("-inf")
    for intervalo in sorted(intervalos, key=lambda x: x.fin):
        if intervalo.inicio >= ultimo_fin:
            resultado.append(intervalo)
            ultimo_fin = intervalo.fin
    return resultado


def main() -> None:
    agenda = [Intervalo(1, 4), Intervalo(3, 5), Intervalo(0, 6),
              Intervalo(5, 7), Intervalo(8, 9), Intervalo(5, 9)]
    print(seleccionar(agenda))


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\seleccion-intervalos\seleccion_intervalos.py
# Salida esperada:
#   [Intervalo(inicio=1, fin=4), Intervalo(inicio=5, fin=7), Intervalo(inicio=8, fin=9)]
# CodeTrace | FIN EJEMPLO DE CONSOLA
