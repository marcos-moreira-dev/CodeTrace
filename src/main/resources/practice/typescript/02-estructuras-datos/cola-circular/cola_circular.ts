// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\02-estructuras-datos\cola-circular\cola_circular.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

export class ColaCircular {
  private readonly datos: number[];
  private inicio = 0;
  private siguiente = 0;
  private cantidad = 0;

  constructor(capacidad: number) {
    if (!Number.isInteger(capacidad) || capacidad <= 0)
      throw new Error("Capacidad inválida");
    this.datos = new Array<number>(capacidad);
  }

  encolar(valor: number): void {
    if (this.cantidad === this.datos.length) throw new Error("Cola llena");
    this.datos[this.siguiente] = valor;
    this.siguiente = (this.siguiente + 1) % this.datos.length;
    this.cantidad++;
  }

  desencolar(): number {
    if (this.cantidad === 0) throw new Error("Cola vacía");
    const valor = this.datos[this.inicio]!;
    this.inicio = (this.inicio + 1) % this.datos.length;
    this.cantidad--;
    return valor;
  }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\cola-circular\MainColaCircular.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainColaCircular.js"
 * Nota: cola_circular.ts es un componente; la salida corresponde al cliente MainColaCircular.ts.
 * Salida esperada:
 *   reporte
 *   correo factura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
