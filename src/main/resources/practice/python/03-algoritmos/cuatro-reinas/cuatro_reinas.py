# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\03-algoritmos\cuatro-reinas\cuatro_reinas.py"
# Ejecutar: python -B ".\03-algoritmos\cuatro-reinas\cuatro_reinas.py"

"""Cliente de backtracking: cuatro reinas sin atacarse.

Se asigna una columna a cada fila y se poda la rama si dos reinas
comparten columna o diagonal. Después de explorar se deshace la
elección. La búsqueda crece de forma combinatoria en el peor caso.
"""
def resolver(n: int) -> list[tuple[int, ...]]:
    if n < 1 or n > 12:
        raise ValueError("Tablero de tamaño 1 a 12")
    columnas: list[int] = []
    soluciones: list[tuple[int, ...]] = []

    def intentar(fila: int) -> None:
        if fila == n:
            soluciones.append(tuple(columnas))
            return
        for columna in range(n):
            if all(c != columna and abs(c - columna) != fila - f
                   for f, c in enumerate(columnas)):
                columnas.append(columna)
                intentar(fila + 1)
                columnas.pop()

    intentar(0)
    return soluciones


def main() -> None:
    print("Soluciones:", resolver(4))


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\03-algoritmos\cuatro-reinas\cuatro_reinas.py
# Salida esperada:
#   Soluciones: [(1, 3, 0, 2), (2, 0, 3, 1)]
# CodeTrace | FIN EJEMPLO DE CONSOLA
