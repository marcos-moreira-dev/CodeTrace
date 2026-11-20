// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\02-estructuras-datos\pila\pila.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

export class Pila<T> {
  private datos: T[] = [];

  apilar(valor: T): void {
    this.datos.push(valor);
  }

  desapilar(): T | undefined {
    return this.datos.pop();
  }

  size(): number {
    return this.datos.length;
  }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\pila\MainPila.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainPila.js"
 * Nota: pila.ts es un componente; la salida corresponde al cliente MainPila.ts.
 * Salida esperada:
 *   Pila correcta
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
