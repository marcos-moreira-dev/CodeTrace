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
