// CodeTrace | Referencia: TypeScript 5.9 / Node.js 24 / ES2022
// Compilar en COPIA: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-sorting" *.ts
// Ejecutar: node "$env:TEMP\ct-sorting\MainInsercionBinaria.js"

import {ordenar} from "./InsercionBinaria";
/**
 * Cliente Insercion Binaria: posicion logaritmica, desplazamientos
 * cuadraticos; comparar con Insertion Sort y Merge Sort.
 */
const datos=[5,1,3,3,2],vacio:number[]=[];
ordenar(datos);ordenar(vacio);
if(datos.join(",")!=="1,2,3,3,5"||vacio.length)throw Error("Insercion binaria");
console.log("Insercion binaria: ["+datos.join(", ")+"]");
/*
 * Salida esperada:
 * Insercion binaria: [1, 2, 3, 3, 5]
 */
