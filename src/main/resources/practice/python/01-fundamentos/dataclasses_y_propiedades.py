# CodeTrace | Referencia: Python 3.12
# Comprobar en PowerShell: python -m py_compile ".\01-fundamentos\dataclasses_y_propiedades.py"
# Ejecutar: python -B ".\01-fundamentos\dataclasses_y_propiedades.py"

"""Cliente de dataclasses, atributos y validación de invariantes.

Una dataclass construye métodos habituales para agrupar datos sin
repetición. frozen=True evita reasignar sus atributos; la validación
se realiza en __post_init__. No sustituye a una entidad mutable
cuando esta necesita mantener un ciclo de vida.
"""
from dataclasses import dataclass


@dataclass(frozen=True, slots=True)
class Producto:
    codigo: str
    precio: float

    def __post_init__(self) -> None:
        if not self.codigo or self.precio < 0:
            raise ValueError("Producto inválido")

    @property
    def precio_con_impuesto(self) -> float:
        return round(self.precio * 1.15, 2)


def main() -> None:
    producto = Producto("S-01", 12.0)
    print(producto)
    print("Con impuesto:", producto.precio_con_impuesto)


if __name__ == "__main__":
    main()
