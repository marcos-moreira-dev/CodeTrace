// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Compilar/Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit 01-fundamentos/uniones-y-narrowing/uniones_y_narrowing.ts
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir dist 01-fundamentos/uniones-y-narrowing/uniones_y_narrowing.ts; node dist/ARCHIVO.js

/**
 * Cliente de tipos unión discriminados y narrowing.
 * TypeScript reconoce qué variante se tiene por la propiedad literal
 * clase, sin casts. El sistema de tipos desaparece al emitir JavaScript.
 */
type Resultado = { clase: "ok"; valor: number }
               | { clase: "error"; mensaje: string };

function informar(r: Resultado): string {
    return r.clase === "ok" ? "Valor: " + r.valor : "Error: " + r.mensaje;
}
console.log(informar({clase:"ok", valor:42}));
console.log(informar({clase:"error", mensaje:"Sin datos"}));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\01-fundamentos\uniones-y-narrowing\uniones_y_narrowing.ts
 *   node "$env:TEMP\ct-ejemplo-ts\uniones_y_narrowing.js"
 * Salida esperada:
 *   Valor: 42
 *   Error: Sin datos
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
