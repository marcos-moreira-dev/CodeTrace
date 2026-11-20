// CodeTrace | Referencia: JavaScript ES2022, Node.js 24.
// Desde PowerShell en una COPIA del paquete:
// Comprobar sintaxis: node --check '.\05-algoritmos\Euclides.js'
// Ejecutar: node '.\05-algoritmos\Euclides.js'

function mcd(a, b) {
  while (b !== 0) [a, b] = [b, a % b];
  return a;
}
console.log("MCD:", mcd(48, 18));
