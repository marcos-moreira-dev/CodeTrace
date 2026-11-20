# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\03-algoritmos\mochila-01\mochila_01.py"
# Ejecutar: python -B ".\03-algoritmos\mochila-01\mochila_01.py"

"""Cliente de programación dinámica: mochila 0/1.

Cada artículo tiene peso y valor; no puede elegirse más de una vez.
La tabla se recorre en orden descendente de capacidad para evitar
reutilizar un artículo. Complejidad O(n*C) tiempo, O(C) espacio;
el costo es seudopolinómico en la capacidad numérica C.
"""
def optimizar(pesos: list[int], valores: list[int], capacidad: int) -> int:
    if len(pesos) != len(valores) or capacidad < 0:
        raise ValueError("Datos incompatibles")
    tabla = [0] * (capacidad + 1)
    for peso, valor in zip(pesos, valores):
        if peso <= 0 or valor < 0:
            raise ValueError("Peso/valor inválido")
        for c in range(capacidad, peso - 1, -1):
            tabla[c] = max(tabla[c], tabla[c - peso] + valor)
    return tabla[capacidad]


def main() -> None:
    print("Beneficio máximo:", optimizar([2, 3, 4, 5], [3, 4, 5, 8], 8))


if __name__ == "__main__":
    main()
