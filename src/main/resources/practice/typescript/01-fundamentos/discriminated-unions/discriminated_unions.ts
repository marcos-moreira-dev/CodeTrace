// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\01-fundamentos\discriminated-unions\discriminated_unions.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\01-fundamentos\discriminated-unions\discriminated_unions.ts'; node "$env:TEMP\ct-ts\discriminated_unions.js"

/**
 * Cliente de uniones discriminadas y exhaustividad con never.
 *
 * Una propiedad literal indica la variante activa. El switch estrecha
 * su tipo y permite comprobar que todos los estados están contemplados.
 * Si se incorpora otra variante, el caso never advierte al compilar.
 */
type Estado = { tipo: "cargando"; porcentaje: number }
    | { tipo: "listo"; archivo: string }
    | { tipo: "error"; mensaje: string };
function describir(e: Estado): string {
    switch (e.tipo) {
        case "cargando": return "Avance: " + e.porcentaje + "%";
        case "listo": return "Archivo: " + e.archivo;
        case "error": return e.mensaje;
        default: {
            const exhaustivo: never = e;
            return exhaustivo;
        }
    }
}
console.log(describir({ tipo: "listo", archivo: "reporte.pdf" }));
