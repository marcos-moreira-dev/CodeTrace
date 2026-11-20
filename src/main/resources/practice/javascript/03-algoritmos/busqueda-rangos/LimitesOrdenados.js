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
