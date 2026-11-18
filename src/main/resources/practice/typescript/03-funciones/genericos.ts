// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\03-funciones\genericos.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

function primero<T>(datos: readonly T[]): T | undefined {
  return datos[0];
}

const numero = primero<number>([4, 5, 6]);
const palabra = primero<string>(["uno", "dos"]);
console.log(numero, palabra);
