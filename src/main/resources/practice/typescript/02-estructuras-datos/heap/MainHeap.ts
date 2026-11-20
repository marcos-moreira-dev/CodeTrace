// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\02-estructuras-datos\heap\MainHeap.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\02-estructuras-datos\heap\MainHeap.ts'; node "$env:TEMP\ct-ts\MainHeap.js"

import { MinHeap } from "./MinHeap";

/**
 * Cliente de una cola de prioridad con un heap binario.
 *
 * A diferencia de una cola FIFO, se atiende según prioridad numérica.
 * El heap está contenido en un arreglo, pero el cliente solo conoce
 * insertar/extraer. Costos: O(log n) para ambas operaciones.
 */
const prioridades = new MinHeap();
for (const prioridad of [7, 2, 9, 1, 5]) prioridades.insertar(prioridad);
while (!prioridades.vacio) console.log(prioridades.extraer());

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\heap\MainHeap.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainHeap.js"
 * Salida esperada:
 *   1
 *   2
 *   5
 *   7
 *   9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
