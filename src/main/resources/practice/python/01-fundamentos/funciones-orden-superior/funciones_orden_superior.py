# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\01-fundamentos\funciones_orden_superior.py"
# Ejecutar: python -B ".\01-fundamentos\funciones_orden_superior.py"

"""Cliente de funciones de orden superior y funciones parciales.

Una función es un valor: puede entregarse como argumento, almacenarse
y devolverse. partial crea otra llamada con argumentos ya fijados;
key permite ordenar objetos según una característica elegida sin
alterar su clase ni inventar interfaces nominales.
"""
from functools import partial


def aplicar(precio: float, tasa: float) -> float:
    return round(precio * (1 + tasa), 2)


def main() -> None:
    impuesto = partial(aplicar, tasa=0.15)
    precios = [12.0, 4.5, 9.0]
    print([impuesto(valor) for valor in precios])
    productos = [{"codigo": "B", "precio": 4}, {"codigo": "A", "precio": 4}]
    print(sorted(productos, key=lambda p: (p["precio"], p["codigo"])))


if __name__ == "__main__":
    main()
