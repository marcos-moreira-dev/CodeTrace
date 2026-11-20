// CodeTrace | Referencia: JavaScript Node.js 22+, Observer
// Compilar: node 07-proyectos/observer/main.js
const { BusEventos } = require("./BusEventos");
const bus = new BusEventos();
bus.registrar(evento => console.log(evento));
bus.publicar("Factura 001");
