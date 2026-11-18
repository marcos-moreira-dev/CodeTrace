// CodeTrace | Referencia: ECMAScript 2022; Node.js 24.
// Desde PowerShell en una COPIA de este paquete:
// Comprobar sintaxis: node --check '.\02-funciones\funciones.js'
// Ejecutar: node '.\02-funciones\funciones.js'

function total(precio, cantidad) {
  return precio * cantidad;
}

const aplicarDescuento = (valor) => valor * 0.9;
console.log(aplicarDescuento(total(12, 3)));
