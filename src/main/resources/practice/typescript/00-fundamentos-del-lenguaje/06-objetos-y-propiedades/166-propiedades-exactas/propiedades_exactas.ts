// Tema: propiedades exactas.
// exactOptionalPropertyTypes diferencia una clave ausente de una clave presente con undefined.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 --exactOptionalPropertyTypes propiedades_exactas.ts
// Diagnóstico esperado: TS2375; no ejecutar.

type Opcion = { nombre?: string };
const correcta: Opcion = {};
const incorrecta: Opcion = { nombre: undefined };
