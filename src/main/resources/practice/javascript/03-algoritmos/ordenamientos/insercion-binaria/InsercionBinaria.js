// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check InsercionBinaria.js
// Ejecutar: node main.js (desde esta carpeta)

/** Binary insertion: comparaciones O(n log n), movimientos O(n²), estable. */
function ordenar(a){
    for(let i=1;i<a.length;i++){
        const x=a[i];let lo=0,hi=i;
        while(lo<hi){
            const m=lo+Math.floor((hi-lo)/2);
            if(a[m]<=x)lo=m+1;else hi=m;
        }
        for(let j=i;j>lo;j--)a[j]=a[j-1];
        a[lo]=x;
    }
}
module.exports={ordenar};
