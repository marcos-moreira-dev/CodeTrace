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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\arbol-bst\MainArbolBST.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainArbolBST.js"
 * Nota: Arbol.ts es un componente; la salida corresponde al cliente MainArbolBST.ts.
 * Salida esperada:
 *   [
 *     1, 3,  4, 6,
 *     7, 8, 10
 *   ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
