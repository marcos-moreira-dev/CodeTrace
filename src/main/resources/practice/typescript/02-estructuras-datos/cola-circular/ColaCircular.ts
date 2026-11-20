// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Compilar/Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit ARCHIVO.ts
// Ejecutar: Consulte Principal.ts

/** Cola genérica de capacidad fija. enqueue/dequeue son O(1). */
export class ColaCircular<T> {
    private readonly datos: (T | undefined)[];
    private inicio = 0;
    private cantidad = 0;
    constructor(private readonly capacidad: number) {
        if (!Number.isInteger(capacidad) || capacidad < 1) throw Error("Capacidad");
        this.datos = new Array<T | undefined>(capacidad);
    }
    encolar(x: T): void {
        if (this.cantidad === this.capacidad) throw Error("Llena");
        this.datos[(this.inicio + this.cantidad) % this.capacidad] = x;
        this.cantidad++;
    }
    extraer(): T {
        if (!this.cantidad) throw Error("Vacía");
        const x = this.datos[this.inicio] as T;
        this.datos[this.inicio] = undefined;
        this.inicio = (this.inicio + 1) % this.capacidad;
        this.cantidad--;
        return x;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\cola-circular\MainColaCircular.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainColaCircular.js"
 * Nota: ColaCircular.ts es un componente; la salida corresponde al cliente MainColaCircular.ts.
 * Salida esperada:
 *   reporte
 *   correo factura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
