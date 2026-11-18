class Nodo<T> {
  siguiente: Nodo<T> | null = null;
  constructor(readonly dato: T) {}
}

export class ListaSimple<T> {
  private primero: Nodo<T> | null = null;

  insertarInicio(dato: T): void {
    const nuevo = new Nodo(dato);
    nuevo.siguiente = this.primero;
    this.primero = nuevo;
  }
}
