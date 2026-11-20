// CodeTrace | Referencia: TypeScript 5.9 / Node.js 24 / ES2022
// Compilar en COPIA: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-sorting" *.ts
// Ejecutar: node "$env:TEMP\ct-sorting\MainOrdenamientos.js"

/**
 * LowerBound -> primer indice a[i]>=x; UpperBound -> a[i]>x.
 * Precondicion arreglo ascendente; O(log n) comparaciones,
 * O(1) espacio. Devuelve posicion de insercion, no -1.
 */
export function lowerBound(a:readonly number[],x:number):number{
    let lo=0,hi=a.length;
    while(lo<hi){
        const m=lo+Math.floor((hi-lo)/2);
        if(a[m]<x)lo=m+1;else hi=m;
    }
    return lo;
}
export function upperBound(a:readonly number[],x:number):number{
    let lo=0,hi=a.length;
    while(lo<hi){
        const m=lo+Math.floor((hi-lo)/2);
        if(a[m]<=x)lo=m+1;else hi=m;
    }
    return lo;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\busqueda-rangos\MainLimitesOrdenados.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainLimitesOrdenados.js"
 * Nota: LimitesOrdenados.ts es un componente; la salida corresponde al cliente MainLimitesOrdenados.ts.
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
