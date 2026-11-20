// CodeTrace | Referencia: TypeScript 5.9 / Node.js 24 / ES2022
// Compilar en COPIA: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-sorting" *.ts
// Ejecutar: node "$env:TEMP\ct-sorting\MainLimitesOrdenados.js"

import {lowerBound,upperBound} from "./LimitesOrdenados";
/**
 * Cliente para intervalo semiabierto de iguales y posicion de
 * insercion. Este algoritmo NO ordena ni es Binary Insertion Sort.
 */
const a=[1,3,3,3,5,9],l=lowerBound(a,3),u=upperBound(a,3);
const pos=lowerBound(a,4);
if(l!==1||u!==4||pos!==4||upperBound(a,4)!==4)throw Error("Rangos");
console.log("Valor 3: ["+l+", "+u+")");
console.log("Insertar 4 en indice: "+pos);
/*
 * Salida esperada:
 * Valor 3: [1, 4)
 * Insertar 4 en indice: 4
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\busqueda-rangos\MainLimitesOrdenados.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainLimitesOrdenados.js"
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
