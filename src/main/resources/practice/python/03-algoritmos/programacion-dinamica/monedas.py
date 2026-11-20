# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B ARCHIVO.py
# Ejecutar: Consulte main.py

"""Algoritmos para mínimo cambio con denominaciones positivas.

DP conserva el mejor resultado para cada monto, con tiempo O(m*k) y
memoria O(m), siendo m el monto y k el número de monedas. El voraz
ordena las monedas y construye una solución factible, no siempre óptima.
"""


def voraz(monedas: list[int], monto: int) -> int:
    validar(monedas, monto)
    total = 0
    for moneda in sorted(monedas, reverse=True):
        cantidad, monto = divmod(monto, moneda)
        total += cantidad
    return total if monto == 0 else -1


def dinamica(monedas: list[int], monto: int) -> int:
    validar(monedas, monto)
    infinito = monto + 1
    dp = [0] + [infinito] * monto
    for actual in range(1, monto + 1):
        for moneda in monedas:
            if moneda <= actual:
                dp[actual] = min(dp[actual], dp[actual - moneda] + 1)
    return -1 if dp[monto] == infinito else dp[monto]


def validar(monedas: list[int], monto: int) -> None:
    if not monedas or monto < 0 or any(moneda <= 0 for moneda in monedas):
        raise ValueError("Monedas y monto deben ser positivos/no negativos")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\programacion-dinamica\main.py
# Nota: monedas.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   Voraz: 3
#   Dinámica: 2
# CodeTrace | FIN EJEMPLO DE CONSOLA
