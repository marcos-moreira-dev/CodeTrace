// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

const { cobrar, conDescuento } = require("./tarifas");
/**
 * Cliente Strategy: funciones y closures en vez de clases innecesarias.
 *
 * El cierre captura un porcentaje sin guardarlo en campos de objeto.
 * cobrar depende del comportamiento recibido, no de la selección de
 * una subclase. El cambio de regla no modifica el contexto.
 */
function main() {
    const normal = valor => valor;
    const fidelidad = conDescuento(0.1);
    if (cobrar(100, fidelidad) !== 90) throw Error("Strategy");
    console.log(cobrar(100, normal), cobrar(100, fidelidad));
}
if (require.main === module) main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\strategy\tarifas\main.js
 * Salida esperada:
 *   100 90
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
