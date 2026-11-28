// Tema: differences between type and interface.
// interface y type se solapan para objetos, pero difieren en fusión, uniones y otros usos del sistema de tipos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 05.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface A {
    x: 'x';
}
interface B {
    y: 'y';
}

type C = A | B; // Union of interfaces
