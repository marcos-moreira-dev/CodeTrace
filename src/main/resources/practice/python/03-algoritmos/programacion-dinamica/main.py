# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B ARCHIVO.py
# Ejecutar: python -B .\03-algoritmos\programacion-dinamica\main.py

"""Cliente: el cambio de monedas evidencia los límites de lo voraz.

El criterio local 'tomar la moneda mayor' obtiene 4+1+1 con {1,3,4}
para un monto de 6. La solución óptima utiliza 3+3. Una tabla DP
reutiliza los montos anteriores y garantiza el mínimo bajo las
precondiciones del módulo monedas.py. No se necesita un framework.
"""
from monedas import dinamica, voraz


def main() -> None:
    monedas = [1, 3, 4]
    monto = 6
    print("Voraz:", voraz(monedas, monto))
    print("Dinámica:", dinamica(monedas, monto))
    assert (voraz(monedas, monto), dinamica(monedas, monto)) == (3, 2)


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\programacion-dinamica\main.py
# Salida esperada:
#   Voraz: 3
#   Dinámica: 2
# CodeTrace | FIN EJEMPLO DE CONSOLA
