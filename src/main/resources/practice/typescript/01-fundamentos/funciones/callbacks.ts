// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\01-fundamentos/funciones\callbacks.ts'
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\01-fundamentos\funciones\async.ts
 *   node "$env:TEMP\ct-ejemplo-ts\async.js"
 * Nota: callbacks.ts es un componente; la salida corresponde al cliente async.ts.
 * Salida esperada:
 *   21
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
