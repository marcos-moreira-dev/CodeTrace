// Tema: Asignabilidad de unknown
// unknown acepta valores de cualquier tipo, pero no permite asignarlos a tipos concretos sin una comprobación previa.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2322, TS2322). No ejecutar.

declare const value: unknown;
const value1: unknown = value;
const value2: any = value;
const value3: boolean = value;
const value4: number = value;
