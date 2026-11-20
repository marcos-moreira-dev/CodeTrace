# CodeTrace | Referencia: Python 3.12
# Compilar/Comprobar: python -B ARCHIVO.py
# Ejecutar: python -B .\02-estructuras-datos\cola-circular\main.py

"""Cliente: turnos de trabajo con cola circular.

En una cola FIFO el primer trabajo en llegar es el primero que sale.
El arreglo circular evita desplazar todos los elementos tras cada salida.
El cliente demuestra también que los índices pueden dar la vuelta sin
alterar el orden lógico. Revisa cola.py para los invariantes de tamaño.
"""
from cola import ColaCircular


def main() -> None:
    trabajos = ColaCircular(3)
    trabajos.encolar("factura")
    trabajos.encolar("correo")
    assert trabajos.desencolar() == "factura"
    trabajos.encolar("respaldo")
    print(trabajos.desencolar(), trabajos.desencolar())
    assert len(trabajos) == 0


if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
