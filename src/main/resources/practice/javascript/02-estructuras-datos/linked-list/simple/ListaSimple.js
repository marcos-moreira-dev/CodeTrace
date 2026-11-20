// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check ListaSimple.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** Linked List simple: insertar en cabeza O(1), recorrer O(n). */
class Nodo{
    constructor(valor,siguiente=null){this.valor=valor;this.siguiente=siguiente;}
}
class ListaSimple{
    constructor(){this.primero=null;this.cantidad=0;}
    agregarPrimero(valor){
        this.primero=new Nodo(valor,this.primero);
        this.cantidad++;
    }
    valores(){
        const salida=[];
        for(let n=this.primero;n!==null;n=n.siguiente)salida.push(n.valor);
        return salida;
    }
}
module.exports={ListaSimple};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\linked-list\simple\main.js
 * Nota: ListaSimple.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   [ 'C', 'B', 'A' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
