// Tema: typescript fundamental comparison rules.
// El retorno más específico es asignable al menos específico, pero no en sentido inverso.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 04.ts
// Diagnóstico esperado: TS2322; no ejecutar.

let x = () => ({ a: 'A' });
let y = () => ({ a: 'A', b: 'B' });
x = y; // Válido
y = x; // Inválido member b is missing
