// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check ".\02-estructuras-datos\heap_minimo.js"
// Ejecutar: node ".\02-estructuras-datos\heap_minimo.js"

/**
 * Cliente de un heap mínimo implementado sobre Array.
 *
 * La raíz almacena el menor valor, no todos los elementos
 * están ordenados. Subir y bajar por el árbol implícito permite
 * O(log n) al insertar o extraer, con acceso O(1) a la raíz.
 */
class MinHeap {
    constructor() { this.a = []; }
    insertar(valor) {
        const a = this.a;
        a.push(valor);
        let i = a.length - 1;
        while (i > 0) {
            const p = Math.floor((i - 1) / 2);
            if (a[p] <= a[i]) break;
            [a[p], a[i]] = [a[i], a[p]];
            i = p;
        }
    }
    extraer() {
        const a = this.a;
        if (!a.length) throw Error("Vacío");
        const primero = a[0], ultimo = a.pop();
        if (a.length) {
            a[0] = ultimo;
            let i = 0;
            while (true) {
                let m = i, iz = i * 2 + 1, de = iz + 1;
                if (iz < a.length && a[iz] < a[m]) m = iz;
                if (de < a.length && a[de] < a[m]) m = de;
                if (m === i) break;
                [a[i], a[m]] = [a[m], a[i]];
                i = m;
            }
        }
        return primero;
    }
    get vacio() { return this.a.length === 0; }
}
const heap = new MinHeap();
[9, 2, 7, 1, 5].forEach(n => heap.insertar(n));
while (!heap.vacio) console.log(heap.extraer());
