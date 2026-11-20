// CodeTrace | Referencia: TypeScript 5.9 (ECMAScript ES2022; Node.js 24 para ejecutar JS).
// Comandos desde la carpeta del paquete, en una COPIA:
// Comprobar tipos sin generar archivos: tsc --noEmit --target ES2022 --module commonjs '.\01-fundamentos/tipos\interfaces.ts'
// Instalar una vez el compilador si no existe: npm install -g typescript@5.9.3

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
