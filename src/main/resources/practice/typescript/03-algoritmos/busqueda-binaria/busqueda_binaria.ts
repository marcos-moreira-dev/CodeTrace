// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\03-algoritmos\busqueda-binaria\busqueda_binaria.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

export function busquedaBinaria(datos: readonly number[], x: number): number {
  let izquierda = 0;
  let derecha = datos.length - 1;
  while (izquierda <= derecha) {
    const medio = izquierda + Math.floor((derecha - izquierda) / 2);
    if (datos[medio] === x) return medio;
    if (datos[medio] < x) izquierda = medio + 1;
    else derecha = medio - 1;
  }
  return -1;
}
