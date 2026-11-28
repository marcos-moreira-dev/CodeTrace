// Tema: uppercase t.
// Uppercase transforma un tipo literal de cadena a mayúsculas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type MyType = Uppercase<'abc'>; // "ABC"
