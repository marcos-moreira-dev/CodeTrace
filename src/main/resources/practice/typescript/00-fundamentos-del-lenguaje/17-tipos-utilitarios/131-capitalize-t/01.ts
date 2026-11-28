// Tema: capitalize t.
// Capitalize convierte a mayúscula el carácter inicial de un tipo literal de cadena.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type MyType = Capitalize<'abc'>; // "Abc"
