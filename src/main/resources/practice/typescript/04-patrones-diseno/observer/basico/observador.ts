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
