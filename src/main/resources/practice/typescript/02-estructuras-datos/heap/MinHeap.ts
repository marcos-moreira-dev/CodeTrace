// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\02-estructuras-datos\heap\MinHeap.ts'
// Ejecutar: ejecutar desde Principal.ts del mismo ejercicio

/**
 * Heap mínimo de enteros sobre arreglo, con operaciones O(log n).
 *
 * Un padre no es mayor que sus hijos. No se ordena todo el arreglo
 * después de cada inserción: se restaura solo el camino necesario.
 */
export class MinHeap {
    private datos: number[] = [];

    insertar(valor: number): void {
        const a = this.datos;
        a.push(valor);
        for (let i = a.length - 1; i > 0;) {
            const padre = Math.floor((i - 1) / 2);
            if (a[padre] <= a[i]) break;
            [a[padre], a[i]] = [a[i], a[padre]];
            i = padre;
        }
    }

    extraer(): number {
        const a = this.datos;
        if (!a.length) throw Error("Heap vacío");
        const primero = a[0];
        const ultimo = a.pop()!;
        if (a.length) {
            a[0] = ultimo;
            for (let i = 0; ;) {
                let menor = i;
                const izq = 2 * i + 1, der = izq + 1;
                if (izq < a.length && a[izq] < a[menor]) menor = izq;
                if (der < a.length && a[der] < a[menor]) menor = der;
                if (i === menor) break;
                [a[i], a[menor]] = [a[menor], a[i]];
                i = menor;
            }
        }
        return primero;
    }

    get vacio(): boolean { return this.datos.length === 0; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\heap\MainHeap.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainHeap.js"
 * Nota: MinHeap.ts es un componente; la salida corresponde al cliente MainHeap.ts.
 * Salida esperada:
 *   1
 *   2
 *   5
 *   7
 *   9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
