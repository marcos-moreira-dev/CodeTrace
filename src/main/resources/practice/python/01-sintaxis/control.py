def sumar_pares(limite: int) -> int:
    total = 0
    for numero in range(limite + 1):
        if numero % 2 == 0:
            total += numero
    return total

print(sumar_pares(10))
