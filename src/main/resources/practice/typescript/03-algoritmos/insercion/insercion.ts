// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\03-algoritmos\insercion\insercion.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

export function insercion(valores: number[]): void {
  for (let i = 1; i < valores.length; i++) {
    const actual = valores[i];
    let j = i - 1;
    while (j >= 0 && valores[j] > actual) {
      valores[j + 1] = valores[j];
      j--;
    }
    valores[j + 1] = actual;
  }
}
