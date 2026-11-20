// CodeTrace | Referencia: JavaScript Node.js 22+, Observer
// Compilar: node 04-patrones-diseno/observer/main.js
const { BusEventos } = require("./BusEventos");
const bus = new BusEventos();
bus.registrar(evento => console.log(evento));
bus.publicar("Factura 001");

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
