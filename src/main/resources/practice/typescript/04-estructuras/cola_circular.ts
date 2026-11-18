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
