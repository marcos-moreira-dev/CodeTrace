# CodeTrace | Referencia: Python 3.12
# Comprobar en COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar desde la carpeta de unidad: python -B main.py
# Con -B no se generan archivos __pycache__ en el proyecto.

"""Cliente de heap: prioridad distinta del orden FIFO.

Una cola FIFO devuelve el trabajo más antiguo; una cola de prioridad
devuelve la prioridad numérica menor. Para igual prioridad usamos un
segundo criterio de llegada; heapq solo garantiza el orden de tuplas.
Esto conecta con Dijkstra, que utiliza una cola de prioridades.
"""
from cola_prioridad import ColaPrioridad


def main() -> None:
    cola = ColaPrioridad()
    cola.encolar("informe", 3)
    cola.encolar("factura", 1)
    cola.encolar("correo", 1)
    salida = [cola.extraer(), cola.extraer(), cola.extraer()]
    assert salida == ["factura", "correo", "informe"]
    print(salida)


if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
