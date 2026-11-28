// Tema: literal types.
// Un tipo literal restringe un valor a una alternativa concreta, no a todo el tipo primitivo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type O = 'a' | 'b' | 'c';
