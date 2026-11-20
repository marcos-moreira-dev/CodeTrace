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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\observer\basico\main.js
 * Nota: Observer.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   [ 42 ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
