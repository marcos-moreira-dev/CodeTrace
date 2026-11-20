// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

const { Emisor } = require("./Observer");
/**
 * Cliente Observer: publicación sin conocer al observador.
 *
 * El callback es una función, no necesariamente una subclase.
 * La función de cancelar controla el ciclo de vida del suscriptor,
 * evitando referencias retenidas tras cerrar una vista.
 */
function main() {
    const emisor = new Emisor(), eventos = [];
    const cancelar = emisor.suscribir(n => eventos.push(n));
    emisor.publicar(42); cancelar(); emisor.publicar(99);
    if (eventos.length !== 1 || eventos[0] !== 42) throw Error("Observer");
    console.log(eventos);
}
if (require.main === module) main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\observer\basico\main.js
 * Salida esperada:
 *   [ 42 ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
