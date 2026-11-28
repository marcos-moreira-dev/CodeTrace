// Tema: object types.
// Un tipo de objeto describe las propiedades y métodos que debe satisfacer una estructura.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface User {
    name: string;
    age: number;
    email?: string;
}
