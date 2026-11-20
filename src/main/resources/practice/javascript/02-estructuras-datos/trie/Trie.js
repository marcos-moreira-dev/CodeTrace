// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check Trie.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** Trie por Map y caracteres Unicode iterable, O(L) por consulta. */
class Nodo{constructor(){this.hijos=new Map();this.final=false;}}
class Trie{
    constructor(){this.raiz=new Nodo();}
    insertar(texto){
        let n=this.raiz;
        for(const c of texto){
            if(!n.hijos.has(c))n.hijos.set(c,new Nodo());
            n=n.hijos.get(c);
        }
        n.final=true;
    }
    nodo(texto){
        let n=this.raiz;
        for(const c of texto){
            n=n.hijos.get(c);
            if(!n)return null;
        }
        return n;
    }
    contiene(x){return this.nodo(x)?.final??false;}
    prefijo(x){return this.nodo(x)!==null;}
}
module.exports={Trie};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\trie\main.js
 * Nota: Trie.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Trie OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
