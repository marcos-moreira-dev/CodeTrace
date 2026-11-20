// CodeTrace | Referencia: JavaScript Node.js 22+, Observer
// Compilar: node 04-patrones-diseno/observer/main.js
const { BusEventos } = require("./BusEventos");
const bus = new BusEventos();
bus.registrar(evento => console.log(evento));
bus.publicar("Factura 001");

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\observer\main.js
 * Salida esperada:
 *   Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
