// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check LimitesOrdenados.js
// Ejecutar: node main.js (desde esta carpeta)

/** Lower: primer >= objetivo. Upper: primero > objetivo. O(log n). */
function lower(a,x){
    let lo=0,hi=a.length;
    while(lo<hi){
        const m=lo+Math.floor((hi-lo)/2);
        if(a[m]<x)lo=m+1;else hi=m;
    }
    return lo;
}
function upper(a,x){
    let lo=0,hi=a.length;
    while(lo<hi){
        const m=lo+Math.floor((hi-lo)/2);
        if(a[m]<=x)lo=m+1;else hi=m;
    }
    return lo;
}
module.exports={lower,upper};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\03-algoritmos\busqueda-rangos\main.js
 * Nota: LimitesOrdenados.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
