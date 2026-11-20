// CodeTrace | Referencia: TypeScript 5.9 / Node.js 24 / ES2022
// Compilar en COPIA: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-sorting" *.ts
// Ejecutar: node "$env:TEMP\ct-sorting\MainOrdenamientos.js"

import {insercion,burbuja,mezcla,heap,rapido} from "./Ordenadores";
/**
 * Cliente comparativo de cinco algoritmos clasicos.
 *
 * Las funciones actuan sobre COPIAS de datos identicos; medir solo
 * tiempo de pared no demuestra Big-O. Cada algoritmo debe manejar
 * tambien vacio y repetidos. Es el mismo escenario que Java/Python.
 */
const metodos:[string,(a:number[])=>void][]=[
    ["Insercion",insercion],["Burbuja",burbuja],["Mezcla",mezcla],
    ["HeapSort",heap],["QuickSort",rapido]
];
const esperado="1,2,3,5,7,8,9";
for(const [nombre,fn] of metodos){
    const datos=[9,3,8,1,7,2,5];
    fn(datos);
    if(datos.join(",")!==esperado)throw Error(nombre);
    console.log(nombre+": ["+datos.join(", ")+"]");
    const vacio:number[]=[],repetidos=[3,1,3,1];
    fn(vacio);fn(repetidos);
    if(vacio.length!==0||repetidos.join(",")!=="1,1,3,3")throw Error("Limites");
}
/*
 * Salida esperada (cinco lineas):
 * Insercion: [1, 2, 3, 5, 7, 8, 9]
 * Burbuja: [1, 2, 3, 5, 7, 8, 9]
 * Mezcla: [1, 2, 3, 5, 7, 8, 9]
 * HeapSort: [1, 2, 3, 5, 7, 8, 9]
 * QuickSort: [1, 2, 3, 5, 7, 8, 9]
 */
