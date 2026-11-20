// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\02-estructuras-datos\linked-list\simple\lista_simple.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

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
