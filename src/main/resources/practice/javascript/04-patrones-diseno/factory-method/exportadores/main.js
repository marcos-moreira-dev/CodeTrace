// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

const { CreadorTexto } = require("./creadores");
/**
 * Cliente Factory Method, distinto de una función fábrica simple.
 *
 * El creador concentra el flujo preparar; una subclase decide
 * el producto. El cliente usa el creador sin conocer el exportador
 * concreto. JavaScript resuelve los métodos por prototipos.
 */
function main() {
    const salida = new CreadorTexto().preparar("Factura 001");
    if (salida !== "TXT: Factura 001") throw Error("Fábrica");
    console.log(salida);
}
if (require.main === module) main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\factory-method\exportadores\main.js
 * Salida esperada:
 *   TXT: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
