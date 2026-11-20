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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\03-algoritmos\ordenamientos\insercion-binaria\main.js
 * Nota: InsercionBinaria.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
