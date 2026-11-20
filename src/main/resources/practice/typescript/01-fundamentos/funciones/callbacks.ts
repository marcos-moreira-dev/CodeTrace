// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\03-funciones\callbacks.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

type Receptor = (mensaje: string) => void;

function notificar(texto: string, receptor: Receptor): void {
  receptor(texto);
}

function mostrar(texto: string): void {
  console.log(texto);
}

notificar("Hola", mostrar);
notificar("Lista", (mensaje) => console.log(mensaje));

export { notificar };
