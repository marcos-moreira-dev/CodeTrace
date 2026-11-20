// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Compilar/Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit ARCHIVO.ts
// Ejecutar: node dist/MainMonedas.js

import { minimo } from "./Monedas";
/**
 * Cliente: un algoritmo voraz no siempre encuentra el óptimo.
 * Con monedas 1,3,4 y monto 6, elegir siempre la mayor produce
 * tres monedas (4+1+1). DP guarda resultados de montos menores
 * y obtiene dos (3+3), sin repetir todos los subproblemas.
 *
 * Compilar desde carpeta: tsc --strict --target ES2022 --module commonjs --outDir dist *.ts
 * Ejecutar: node dist/MainMonedas.js
 */
console.log("Mínimo:", minimo([1,3,4], 6));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\programacion-dinamica\MainMonedas.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainMonedas.js"
 * Salida esperada:
 *   Mínimo: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
