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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
