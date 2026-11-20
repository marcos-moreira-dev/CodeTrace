# CodeTrace | Referencia: Python 3.12+, proyecto Factory Method
# Compilar: python 04-patrones-diseno/factory-method/main.py
from creadores import CreadorTexto

def main() -> None:
    print(CreadorTexto().preparar("Factura 001"))

if __name__ == "__main__":
    main()
