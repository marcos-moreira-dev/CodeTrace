// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

const { Legado, Adaptador } = require("./sensores");
/**
 * Cliente Adapter: el negocio consume Celsius y no Fahrenheit.
 *
 * No hace falta una interface en JS, el contrato se verifica
 * dinámicamente al llamar a leer(). La adaptación encapsula la
 * conversión en un único objeto en vez de copiarla por cliente.
 */
function main() {
    const sensor = new Adaptador(new Legado());
    if (sensor.leer() !== 25) throw Error("Adapter");
    console.log("Celsius:", sensor.leer());
}
if (require.main === module) main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\adapter\unidades\main.js
 * Salida esperada:
 *   Celsius: 25
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
