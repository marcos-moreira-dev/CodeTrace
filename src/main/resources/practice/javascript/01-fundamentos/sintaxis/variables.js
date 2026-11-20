// CodeTrace | Referencia: ECMAScript 2022; Node.js 24.
// Desde PowerShell en una COPIA de este paquete:
// Comprobar sintaxis: node --check '.\01-fundamentos/sintaxis\variables.js'
// Ejecutar: node '.\01-fundamentos/sintaxis\variables.js'

const nombre = "CodeTrace";
let sesiones = 2;
const configuracion = { sonido: false, turbo: true };
sesiones += 1;
console.log(nombre, sesiones, configuracion.turbo);
