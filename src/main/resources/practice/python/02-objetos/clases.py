class Contador:
    def __init__(self) -> None:
        self.valor = 0

    def incrementar(self) -> int:
        self.valor += 1
        return self.valor

contador = Contador()
print(contador.incrementar())
