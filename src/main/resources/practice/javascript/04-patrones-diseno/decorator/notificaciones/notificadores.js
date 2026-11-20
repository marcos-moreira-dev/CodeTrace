// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

class Consola { enviar(m) { console.log(m); } }
/** Conserva el contrato del componente decorado y delega. */
class Prefijo {
    constructor(interno, etiqueta) {
        if (typeof interno?.enviar !== "function") throw Error("Contrato");
        this.interno = interno; this.etiqueta = etiqueta;
    }
    enviar(m) { this.interno.enviar(this.etiqueta + m); }
}
module.exports = { Consola, Prefijo };
