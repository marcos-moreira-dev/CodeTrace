# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\03-algoritmos\merge_sort.py"
# Ejecutar: python -B ".\03-algoritmos\merge_sort.py"

"""Cliente de Merge Sort: dividir, resolver y combinar.

La recursión separa el arreglo hasta subproblemas de un elemento.
Combinar exige O(n) operaciones por nivel y hay O(log n) niveles:
O(n log n) tiempo. Esta variante asigna listas adicionales O(n).
"""
def ordenar(datos: list[int]) -> list[int]:
    if len(datos) <= 1:
        return datos.copy()
    mitad = len(datos) // 2
    izq, der = ordenar(datos[:mitad]), ordenar(datos[mitad:])
    resultado: list[int] = []
    i = j = 0
    while i < len(izq) and j < len(der):
        if izq[i] <= der[j]:
            resultado.append(izq[i])
            i += 1
        else:
            resultado.append(der[j])
            j += 1
    return resultado + izq[i:] + der[j:]


def main() -> None:
    entrada = [9, 1, 8, 4, 2, 7]
    print(ordenar(entrada))
    print("La entrada permanece intacta:", entrada)


if __name__ == "__main__":
    main()
