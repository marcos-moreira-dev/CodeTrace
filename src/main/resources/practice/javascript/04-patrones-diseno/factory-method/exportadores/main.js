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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
