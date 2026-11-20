// CodeTrace | Referencia: TypeScript 5.9 / Node.js 24 / ES2022
// Compilar en COPIA: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-sorting" *.ts
// Ejecutar: node "$env:TEMP\ct-sorting\MainOrdenamientos.js"

/**
 * Binary Insertion Sort: encuentra posicion con upper_bound.
 * Reduce comparaciones O(n log n), NO desplazamientos O(n²);
 * sigue siendo O(n²) en total para un arreglo. Estable, in-place.
 */
export function ordenar(a:number[]):void{
    for(let i=1;i<a.length;i++){
        const clave=a[i];let lo=0,hi=i;
        while(lo<hi){
            const m=lo+Math.floor((hi-lo)/2);
            if(a[m]<=clave)lo=m+1;else hi=m;
        }
        for(let j=i;j>lo;j--)a[j]=a[j-1];
        a[lo]=clave;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\ordenamientos\insercion-binaria\MainInsercionBinaria.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainInsercionBinaria.js"
 * Nota: InsercionBinaria.ts es un componente; la salida corresponde al cliente MainInsercionBinaria.ts.
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
