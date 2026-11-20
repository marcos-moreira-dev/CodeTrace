// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\01-fundamentos\readonly_e_inmutabilidad.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\01-fundamentos\readonly_e_inmutabilidad.ts'; node "$env:TEMP\ct-ts\readonly_e_inmutabilidad.js"

/**
 * Cliente de readonly frente a inmutabilidad real.
 *
 * Readonly<T> impide escrituras en el sistema de tipos de TS, pero
 * no congela los objetos JavaScript. Esta función crea un nuevo objeto
 * para no modificar el argumento. Object.freeze ofrece otra garantía
 * de ejecución (superficial) cuando es necesaria.
 */
type Punto = Readonly<{ x: number; y: number }>;
function trasladar(p: Punto, dx: number, dy: number): Punto {
    return { x: p.x + dx, y: p.y + dy };
}
const inicial: Punto = { x: 5, y: 10 };
console.log(inicial, trasladar(inicial, 2, -3));
