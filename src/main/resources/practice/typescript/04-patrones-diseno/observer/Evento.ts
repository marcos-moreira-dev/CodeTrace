// CodeTrace | Referencia: TypeScript 5+, proyecto Observer
// Compilar: npx tsc --target ES2022 --module commonjs 04-patrones-diseno/observer/*.ts
export type Evento = { tipo: string; detalle: string };
export type Observador = (evento: Evento) => void;

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\observer\MainObserver.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainObserver.js"
 * Nota: Evento.ts es un componente; la salida corresponde al cliente MainObserver.ts.
 * Salida esperada:
 *   venta Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
