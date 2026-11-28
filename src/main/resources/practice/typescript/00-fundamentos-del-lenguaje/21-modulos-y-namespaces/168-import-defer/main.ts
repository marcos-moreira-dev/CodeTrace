// Tema: import defer.
// La importación diferida retrasa la evaluación hasta usar una exportación. Requiere TypeScript 7 y un runtime compatible; solo comprobación estática.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 --module ESNext --moduleResolution Bundler main.ts modulo.ts
// Salida esperada: comprobación de tipos; ejecutar solo en el entorno indicado, no en Node por defecto.

import defer * as datos from './modulo.js';
const valor:number=datos.valor;
