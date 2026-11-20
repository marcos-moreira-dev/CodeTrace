// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\04-patrones-diseno\strategy.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

interface Descuento {
  aplicar(total: number): number;
}

class DescuentoFijo implements Descuento {
  constructor(private porcentaje: number) {}

  aplicar(total: number): number {
    return total * (1 - this.porcentaje);
  }
}

const estrategia: Descuento = new DescuentoFijo(0.10);
console.log(estrategia.aplicar(100));
