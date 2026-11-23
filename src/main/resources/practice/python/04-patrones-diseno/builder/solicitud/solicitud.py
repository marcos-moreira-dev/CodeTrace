# CodeTrace | Referencia: Python 3.12
# Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
# Dominio: Petición de consulta de estadísticas del torneo.
# Patrón builder: Construir una solicitud gradualmente sin un constructor enorme.
# Glosario: Estadísticas: datos agregados de los resultados de las rondas.
# Comprobar desde COPIA de esta unidad: python -c "import ast,pathlib; ast.parse(pathlib.Path('solicitud.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py (cliente de esta carpeta, sin __pycache__)

"""Builder: construcción gradual de un producto validado e inmutable.

dataclass(frozen=True) conserva un estado estable y el Builder recopila
opciones. Con dos campos simples puede ser excesivo; aquí cabeceras
opcionales y validación justifican la separación.
"""
from dataclasses import dataclass

@dataclass(frozen=True)
class Solicitud:
    ruta: str
    metodo: str
    cabeceras: tuple[str,...]

class SolicitudBuilder:
    def __init__(self) -> None:
        self._ruta = ""
        self._metodo = "GET"
        self._cabeceras: list[str] = []

    def ruta(self, valor: str) -> "SolicitudBuilder":
        self._ruta = valor; return self

    def metodo(self, valor: str) -> "SolicitudBuilder":
        self._metodo = valor; return self

    def cabecera(self, valor: str) -> "SolicitudBuilder":
        self._cabeceras.append(valor); return self

    def construir(self) -> Solicitud:
        if not self._ruta.startswith("/") or not self._metodo:
            raise ValueError("Solicitud inválida")
        return Solicitud(self._ruta,self._metodo,tuple(self._cabeceras))

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\builder\solicitud\main.py
# Nota: solicitud.py es un componente; la salida corresponde al cliente main.py.
# Salida esperada:
#   POST /facturas (1 cabecera)
# CodeTrace | FIN EJEMPLO DE CONSOLA
