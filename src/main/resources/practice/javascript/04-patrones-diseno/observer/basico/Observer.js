// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

/** Emisor de cambios con listeners y función de desuscripción. */
class Emisor {
    constructor() { this.oyentes = new Set(); }
    suscribir(fn) {
        if (typeof fn !== "function") throw TypeError("Callback");
        this.oyentes.add(fn);
        return () => this.oyentes.delete(fn);
    }
    publicar(valor) {
        for (const oyente of [...this.oyentes]) oyente(valor);
    }
}
module.exports = { Emisor };
