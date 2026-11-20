// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

class Legado { fahrenheit() { return 77; } }
/** Adapter traduce el proveedor externo al contrato leer() del dominio. */
class Adaptador {
    constructor(legado) { this.legado = legado; }
    leer() { return (this.legado.fahrenheit() - 32) * 5 / 9; }
}
module.exports = { Legado, Adaptador };
