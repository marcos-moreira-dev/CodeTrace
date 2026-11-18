interface Producto {
  id: string;
  precio: number;
  disponible?: boolean;
}

function subtotal(producto: Producto, cantidad: number): number {
  return producto.precio * cantidad;
}

const libro: Producto = { id: "L1", precio: 12 };
console.log(subtotal(libro, 2));
