// CodeTrace | Referencia: JavaScript ES2022, Node.js 24.
// Desde PowerShell en una COPIA del paquete:
// Comprobar sintaxis: node --check '.\03-algoritmos\euclides\Euclides.js'
// Ejecutar: node '.\03-algoritmos\euclides\Euclides.js'

function mcd(a, b) {
  while (b !== 0) [a, b] = [b, a % b];
  return a;
}
console.log("MCD:", mcd(48, 18));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\03-algoritmos\euclides\Euclides.js
 * Salida esperada:
 *   MCD: 6
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
