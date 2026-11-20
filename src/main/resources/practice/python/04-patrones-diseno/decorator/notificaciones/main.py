# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Cliente Decorator: añadir comportamiento por composición.

Un Notificador puede envolverse por capas sin crear subclases por
cada combinación. Python Protocol comprueba estructuralmente el
método esperado durante análisis estático, pero no impide llamar
dinámicamente a objetos compatibles durante la ejecución.
"""
from decorator_objetos import Consola, ConPrefijo, Notificador


def main() -> None:
    emisor: Notificador = ConPrefijo(Consola(), "[INFO] ")
    emisor.enviar("Proceso terminado")


if __name__ == "__main__":
    main()

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\decorator\notificaciones\main.py
# Salida esperada:
#   Mensaje: [INFO] Proceso terminado
# CodeTrace | FIN EJEMPLO DE CONSOLA
