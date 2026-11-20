// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

/** Cola circular FIFO de capacidad fija, Enqueue/Dequeue O(1). */
class ColaCircular {
    constructor(n) {
        if (!Number.isInteger(n) || n < 1) throw Error("Capacidad");
        this.a = Array(n); this.inicio = this.fin = this.cantidad = 0;
    }
    encolar(v) {
        if (this.cantidad === this.a.length) throw Error("Llena");
        this.a[this.fin] = v;
        this.fin = (this.fin + 1) % this.a.length;
        this.cantidad++;
    }
    desencolar() {
        if (!this.cantidad) throw Error("Vacía");
        const v = this.a[this.inicio];
        this.inicio = (this.inicio + 1) % this.a.length;
        this.cantidad--;
        return v;
    }
}
module.exports = { ColaCircular };
