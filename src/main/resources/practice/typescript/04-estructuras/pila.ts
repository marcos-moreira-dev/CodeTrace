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
