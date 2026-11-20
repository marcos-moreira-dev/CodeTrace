// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\01-fundamentos/funciones\genericos.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

function primero<T>(datos: readonly T[]): T | undefined {
  return datos[0];
}

const numero = primero<number>([4, 5, 6]);
const palabra = primero<string>(["uno", "dos"]);
console.log(numero, palabra);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\01-fundamentos\funciones\genericos.ts
 *   node "$env:TEMP\ct-ejemplo-ts\genericos.js"
 * Salida esperada:
 *   4 uno
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
