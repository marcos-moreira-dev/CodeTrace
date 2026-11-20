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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\grafos\ponderados\main.js
 * Nota: GrafoPonderado.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   [ { destino: 'UIO', peso: 12 }, { destino: 'CUE', peso: 18 } ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
