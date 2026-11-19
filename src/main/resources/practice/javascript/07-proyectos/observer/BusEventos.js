// CodeTrace | Referencia: JavaScript Node.js 22+, Observer
// Compilar: node 07-proyectos/observer/main.js
class BusEventos {
    constructor() { this.observadores = []; }
    registrar(handler) { this.observadores.push(handler); }
    publicar(evento) {
        for (const handler of this.observadores) handler(evento);
    }
}
module.exports = { BusEventos };
