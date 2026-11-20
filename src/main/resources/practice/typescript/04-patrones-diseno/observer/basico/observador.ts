// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\04-patrones-diseno\observer\basico\observador.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

type Escucha = (nuevoValor: number) => void;

export class Observador {
  private readonly oyentes: Escucha[] = [];
  private valor = 0;

  suscribir(escucha: Escucha): void {
    this.oyentes.push(escucha);
  }

  actualizar(nuevoValor: number): void {
    this.valor = nuevoValor;
    for (const escucha of this.oyentes) escucha(this.valor);
  }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\observer\basico\MainObserverBasico.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainObserverBasico.js"
 * Nota: observador.ts es un componente; la salida corresponde al cliente MainObserverBasico.ts.
 * Salida esperada:
 *   [ 'pantalla:42', 'auditoria:42' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
