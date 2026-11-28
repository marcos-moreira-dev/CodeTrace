// Tema: Inferencia de objetos
// El arreglo contiene objetos con x. filter recibe cada objeto inferido y rechaza una propiedad inexistente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2339). No ejecutar.

const items = [{ x: 1 }, { x: 2 }];
const result = items.filter(item => item.y);
