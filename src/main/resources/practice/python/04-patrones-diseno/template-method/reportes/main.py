# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Template Method: una secuencia fija con pasos extensibles.

Contrastar con Strategy (función pasada como dependencia): aquí la
política se especializa mediante herencia. En Python también podría
expresarse con composición, pero mostrar ABC hace la equivalencia
conceptual con Java transparente.
"""
from reporte import ProcesoReporte, ReporteTexto, ReporteCsv

def main() -> None:
    a: ProcesoReporte=ReporteTexto()
    b: ProcesoReporte=ReporteCsv()
    assert "Total: 15" in a.ejecutar("ventas",15)
    assert "ventas,15" in b.ejecutar("ventas",15)
    print(a.ejecutar("ventas",15)+b.ejecutar("ventas",15),end="")

if __name__ == "__main__":
    main()

# Salida esperada de consola (cuatro líneas):
#   == ventas ==
#   Total: 15
#   titulo,total
#   ventas,15
# El método plantilla valida y fija el orden; las subclases generan cada formato.
