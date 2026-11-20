// CodeTrace | Referencia: JavaScript ES2022, Node.js 24.
// Desde PowerShell en una COPIA del paquete:
// Comprobar sintaxis: node --check '.\04-patrones-diseno\observer\basico\Observer.js'
// Ejecutar: node '.\04-patrones-diseno\observer\basico\Observer.js'

class Emisor {
  constructor() { this.oyentes = []; }
  suscribir(callback) { this.oyentes.push(callback); }
  actualizar(valor) {
    for (const callback of this.oyentes) callback(valor);
  }
}
const emisor = new Emisor();
emisor.suscribir(valor => console.log("Cambio:", valor));
emisor.actualizar(42);
