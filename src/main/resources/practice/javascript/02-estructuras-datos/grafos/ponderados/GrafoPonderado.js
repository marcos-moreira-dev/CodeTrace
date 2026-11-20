// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check GrafoPonderado.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/**
 * Grafo dirigido con Map y listas de aristas.
 * Memoria O(V+E); no incluye un algoritmo de rutas.
 */
class GrafoPonderado{
    constructor(){this.ady=new Map();}
    conectar(origen,destino,peso){
        if(!Number.isFinite(peso))throw Error("Peso");
        if(!this.ady.has(origen))this.ady.set(origen,[]);
        if(!this.ady.has(destino))this.ady.set(destino,[]);
        this.ady.get(origen).push({destino,peso});
    }
    vecinos(origen){
        if(!this.ady.has(origen))throw Error("Vértice");
        return this.ady.get(origen).map(e=>({...e}));
    }
}
module.exports={GrafoPonderado};
