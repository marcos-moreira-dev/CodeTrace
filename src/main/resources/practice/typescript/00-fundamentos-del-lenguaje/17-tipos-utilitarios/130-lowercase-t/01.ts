// Tema: lowercase t.
// Lowercase transforma un tipo literal de cadena a minúsculas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type MyType = Lowercase<'ABC'>; // "abc"
