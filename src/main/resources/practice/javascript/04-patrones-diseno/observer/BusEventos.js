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
