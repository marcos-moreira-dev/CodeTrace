// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Compilar/Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit 01-fundamentos/genericos-y-comparadores/genericos_y_comparadores.ts
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir dist 01-fundamentos/genericos-y-comparadores/genericos_y_comparadores.ts; node dist/ARCHIVO.js

/**
 * Cliente de genéricos y funciones de comparación.
 * La función ordena una copia sin modificar el original. Su criterio
 * establece precio primero e identificador en caso de empate.
 * El parámetro T permite cambiar el tipo sin duplicar el algoritmo.
 */
type Comparador<T> = (a: T, b: T) => number;
type Producto = { id: string; precio: number };

function ordenar<T>(items: readonly T[], cmp: Comparador<T>): T[] {
    return [...items].sort(cmp);
}
const datos: Producto[] = [{id:"B", precio:2},{id:"A", precio:2},{id:"C", precio:1}];
console.log(ordenar(datos, (a, b) => a.precio - b.precio || a.id.localeCompare(b.id)));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\01-fundamentos\genericos-y-comparadores\genericos_y_comparadores.ts
 *   node "$env:TEMP\ct-ejemplo-ts\genericos_y_comparadores.js"
 * Salida esperada:
 *   [
 *     { id: 'C', precio: 1 },
 *     { id: 'A', precio: 2 },
 *     { id: 'B', precio: 2 }
 *   ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
