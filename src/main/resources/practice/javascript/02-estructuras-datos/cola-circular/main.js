// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

const { ColaCircular } = require("./cola");
/**
 * Cliente FIFO: el índice de escritura vuelve al principio.
 *
 * No usar Array.shift(), que desplaza índices y puede costar O(n).
 * Esta cola conserva orden en cada vuelta del buffer y realiza
 * una cantidad constante de operaciones por inserción/extracción.
 */
function main() {
    const q = new ColaCircular(3);
    [1, 2, 3].forEach(x => q.encolar(x));
    if (q.desencolar() !== 1) throw Error("FIFO");
    q.encolar(4);
    const out = [q.desencolar(), q.desencolar(), q.desencolar()];
    if (out.join(",") !== "2,3,4") throw Error("Circular");
    console.log(out);
}
if (require.main === module) main();
