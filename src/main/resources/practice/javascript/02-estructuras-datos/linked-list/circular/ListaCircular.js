// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check ListaCircular.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** Lista circular simple: último.siguiente apunta al primero. */
class Nodo{constructor(valor){this.valor=valor;this.siguiente=this;}}
class ListaCircular{
    constructor(){this.ultimo=null;this.cantidad=0;}
    agregar(valor){
        const n=new Nodo(valor);
        if(this.ultimo){
            n.siguiente=this.ultimo.siguiente;
            this.ultimo.siguiente=n;
        }
        this.ultimo=n;this.cantidad++;
    }
    rotar(){if(this.ultimo)this.ultimo=this.ultimo.siguiente;}
    valores(){
        const r=[];
        if(!this.ultimo)return r;
        let n=this.ultimo.siguiente;
        for(let i=0;i<this.cantidad;i++){r.push(n.valor);n=n.siguiente;}
        return r;
    }
}
module.exports={ListaCircular};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\linked-list\circular\main.js
 * Nota: ListaCircular.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   [ 'Luis', 'Mia', 'Ana' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
