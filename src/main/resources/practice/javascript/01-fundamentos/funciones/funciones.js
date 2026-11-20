// CodeTrace | Referencia: ECMAScript 2022; Node.js 24.
// Desde PowerShell en una COPIA de este paquete:
// Comprobar sintaxis: node --check '.\01-fundamentos/funciones\funciones.js'
// Ejecutar: node '.\01-fundamentos/funciones\funciones.js'

function total(precio, cantidad) {
  return precio * cantidad;
}

const aplicarDescuento = (valor) => valor * 0.9;
console.log(aplicarDescuento(total(12, 3)));
