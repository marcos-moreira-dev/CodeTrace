// Tema: literal inference.
// La aserción conserva la unión X como tipo estático; no valida datos en ejecución.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 04.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = 'a' | 'b';
let o = { x: 'a' as X };
const aceptar = (x: X): X => x;
aceptar(o.x);
