// CodeTrace | Referencia: JavaScript ES2022, Node.js 24.
// Desde PowerShell en una COPIA del paquete:
// Comprobar sintaxis: node --check '.\02-estructuras-datos\pila\Pila.js'
// Ejecutar: node '.\02-estructuras-datos\pila\Pila.js'

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\pila\Pila.js
 * Salida esperada:
 *   8
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
