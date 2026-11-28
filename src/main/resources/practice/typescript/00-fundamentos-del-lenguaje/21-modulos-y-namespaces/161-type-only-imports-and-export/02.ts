// Tema: type only imports and export.
// Las dependencias y reexportaciones solo de tipos no generan importaciones de valores; los alias evitan colisiones.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 --module NodeNext --moduleResolution NodeNext 02.ts mod.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

import type T from './mod.js';
import type { A, B } from './mod.js';
import type * as Types from './mod.js';
export type { T };
export type { default as OtroT } from './mod.js';
const a: A = { a: 1 };
const b: B = { b: 'x' };
const c: Types.A = a;
