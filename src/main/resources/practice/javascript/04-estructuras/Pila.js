// CodeTrace | Referencia: JavaScript ES2022, Node.js 24.
// Desde PowerShell en una COPIA del paquete:
// Comprobar sintaxis: node --check '.\04-estructuras\Pila.js'
// Ejecutar: node '.\04-estructuras\Pila.js'

class Pila {
  constructor() { this.datos = []; }
  apilar(valor) { this.datos.push(valor); }
  desapilar() {
    if (this.datos.length === 0) throw new Error("Pila vacía");
    return this.datos.pop();
  }
}
const pila = new Pila();
pila.apilar(12); pila.apilar(8);
console.log(pila.desapilar());
