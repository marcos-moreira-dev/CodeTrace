# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B 01-fundamentos/generadores-y-comprensiones/generadores_y_comprensiones.py
# Ejecutar: python -B .\01-fundamentos\generadores-y-comprensiones\generadores_y_comprensiones.py

"""Cliente de repaso: comprensiones, generadores y evaluación diferida.

Una comprensión crea una lista de inmediato; un generador produce valores
a demanda y evita almacenar todos los resultados. Ambas expresan una
transformación, pero poseen compromisos distintos de memoria.
El ejemplo usa un dominio acotado y compara sus resultados.
"""
from collections.abc import Iterator


def cuadrados_pares(limite: int) -> Iterator[int]:
    for numero in range(limite):
        if numero % 2 == 0:
            yield numero * numero


def main() -> None:
    lista = [n * n for n in range(10) if n % 2 == 0]
    generador = cuadrados_pares(10)
    print("Lista materializada:", lista)
    print("Generador consumido:", list(generador))


if __name__ == "__main__":
    main()
