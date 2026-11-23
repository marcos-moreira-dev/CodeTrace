# CodeTrace | Referencia: Python 3.12
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Petición de consulta de estadísticas del torneo.
# Patrón builder: Construir una solicitud gradualmente sin un constructor enorme.
# Glosario: Estadísticas: datos agregados de los resultados de las rondas.
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
    print(f"{s.metodo} {s.ruta} ({len(s.cabeceras)} cabecera)")

if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   POST /facturas (1 cabecera)
# La validación del Builder entrega un producto inmutable con una cabecera.

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\builder\solicitud\main.py
# Salida esperada:
#   POST /facturas (1 cabecera)
# CodeTrace | FIN EJEMPLO DE CONSOLA
