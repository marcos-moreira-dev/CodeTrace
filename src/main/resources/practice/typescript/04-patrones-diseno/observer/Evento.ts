// CodeTrace | Referencia: TypeScript 5+, proyecto Observer
// Compilar: npx tsc --target ES2022 --module commonjs 04-patrones-diseno/observer/*.ts
export type Evento = { tipo: string; detalle: string };
export type Observador = (evento: Evento) => void;
