# CodeTrace | Referencia: Python 3.12+, proyecto Factory Method
# Compilar: python 04-patrones-diseno/factory-method/main.py
from creadores import CreadorTexto

def main() -> None:
    print(CreadorTexto().preparar("Factura 001"))

if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\factory-method\main.py
# Salida esperada:
#   TEXTO: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
