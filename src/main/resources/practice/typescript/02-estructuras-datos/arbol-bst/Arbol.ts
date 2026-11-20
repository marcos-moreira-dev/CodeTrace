// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\02-estructuras-datos\arbol-bst\Arbol.ts'
// Ejecutar: ejecutar desde Principal.ts del mismo ejercicio

/**
 * BST genérico: organización mediante una función comparadora.
 *
 * Valores menores a la izquierda y mayores a la derecha; O(h)
 * para insertar y buscar, donde h es la altura. Esta versión no
 * aplica rotaciones AVL y puede degenerarse hasta O(n).
 */
class Nodo<T> {
    constructor(
        public valor: T,
        public izquierdo: Nodo<T> | null = null,
        public derecho: Nodo<T> | null = null
    ) {}
}
export class Arbol<T> {
    private raiz: Nodo<T> | null = null;
    constructor(private readonly comparar: (a: T, b: T) => number) {}

    insertar(valor: T): void {
        if (!this.raiz) { this.raiz = new Nodo(valor); return; }
        let actual = this.raiz;
        while (true) {
            const orden = this.comparar(valor, actual.valor);
            if (orden === 0) return;
            if (orden < 0) {
                if (!actual.izquierdo) { actual.izquierdo = new Nodo(valor); return; }
                actual = actual.izquierdo;
            } else {
                if (!actual.derecho) { actual.derecho = new Nodo(valor); return; }
                actual = actual.derecho;
            }
        }
    }

    inorden(): T[] {
        const salida: T[] = [];
        const visitar = (n: Nodo<T> | null): void => {
            if (n === null) return;
            visitar(n.izquierdo);
            salida.push(n.valor);
            visitar(n.derecho);
        };
        visitar(this.raiz);
        return salida;
    }
}
