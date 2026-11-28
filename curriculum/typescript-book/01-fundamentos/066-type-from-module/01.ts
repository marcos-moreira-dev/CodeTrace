// Tema:
// Una consulta de tipos puede referirse a declaraciones exportadas de un módulo sin importar
// un valor en ejecución.

// calc.ts
export const add = (x: number, y: number) => x + y;
// index.ts
import { add } from 'calc';
const r = add(1, 2); // r is number
