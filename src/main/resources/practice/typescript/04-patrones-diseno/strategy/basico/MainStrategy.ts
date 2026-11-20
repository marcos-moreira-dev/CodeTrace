// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: node "$env:TEMP\ct-ts-unit\MainStrategy.js"

import { DescuentoFijo, Facturador, type Descuento } from "./strategy";

/**
 * Cliente Strategy: comparar dos reglas sobre un mismo contexto.
 *
 * Una política se expresa como clase y otra como función anónima:
 * TypeScript verifica compatibilidad estructural del contrato
 * Descuento. El programa JavaScript emitido no conserva la interfaz.
 */
const facturador = new Facturador();
const reducido = new DescuentoFijo(0.10);
const normal: Descuento = { aplicar: subtotal => subtotal };
console.log(facturador.cobrar(100, normal), facturador.cobrar(100, reducido));
if (facturador.cobrar(100, reducido) !== 90) throw Error("Descuento");

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\strategy\basico\MainStrategy.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainStrategy.js"
 * Salida esperada:
 *   100 90
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
