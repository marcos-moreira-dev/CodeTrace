// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check ColaEnlazada.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** Queue FIFO por nodos: head/tail, O(1) por operación. */
class Nodo{constructor(valor){this.valor=valor;this.siguiente=null;}}
class ColaEnlazada{
    constructor(){this.primero=null;this.ultimo=null;this.cantidad=0;}
    encolar(valor){
        const n=new Nodo(valor);
        if(this.ultimo)this.ultimo.siguiente=n;else this.primero=n;
        this.ultimo=n;this.cantidad++;
    }
    desencolar(){
        if(!this.primero)throw Error("Cola vacía");
        const dato=this.primero.valor;
        this.primero=this.primero.siguiente;
        if(!this.primero)this.ultimo=null;
        this.cantidad--;return dato;
    }
}
module.exports={ColaEnlazada};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\cola-enlazada\main.js
 * Nota: ColaEnlazada.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Cola enlazada correcta
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
