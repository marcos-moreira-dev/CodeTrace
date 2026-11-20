# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B ARCHIVO.py
# Ejecutar: python -B .\04-patrones-diseno\strategy\main.py

"""Cliente Strategy idiomático: pasar funciones como dependencias.

El comportamiento variable es el cálculo de la tarifa. En Python
una función es un objeto de primera clase, así que Strategy puede
implementarse de forma más ligera que una jerarquía de clases Java.
Comparar esta versión con la de Java ilustra diseño guiado por dominio.
"""
from estrategias import Estrategia, normal, promocion


def cobrar(subtotal: float, estrategia: Estrategia) -> float:
    return estrategia(subtotal)


def main() -> None:
    print("Normal:", cobrar(100, normal))
    print("Promoción:", cobrar(100, promocion))


if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Normal:
#   Promoción:
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
