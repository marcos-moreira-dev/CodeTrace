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
