// CodeTrace | Referencia: JavaScript Node.js 22+, Observer
// Compilar: node 04-patrones-diseno/observer/main.js
class BusEventos {
    constructor() { this.observadores = []; }
    registrar(handler) { this.observadores.push(handler); }
    publicar(evento) {
        for (const handler of this.observadores) handler(evento);
    }
}
module.exports = { BusEventos };

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\observer\main.js
 * Nota: BusEventos.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
