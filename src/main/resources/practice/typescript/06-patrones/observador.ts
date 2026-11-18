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
