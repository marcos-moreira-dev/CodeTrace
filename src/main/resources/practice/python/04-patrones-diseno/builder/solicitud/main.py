# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Builder: construcción fluida, producto inmutable y validación.

El cliente declara cada opción explícitamente. El Builder se encarga
de validar, mientras la dataclass congelada representa el resultado.
Esto equivale al patrón de Java, adaptado a la sintaxis Python.
"""
from solicitud import SolicitudBuilder

def main() -> None:
    s=(SolicitudBuilder().ruta("/facturas").metodo("POST")
       .cabecera("Content-Type: application/json").construir())
    assert s.ruta=="/facturas" and len(s.cabeceras)==1
    print(s)

if __name__ == "__main__":
    main()
