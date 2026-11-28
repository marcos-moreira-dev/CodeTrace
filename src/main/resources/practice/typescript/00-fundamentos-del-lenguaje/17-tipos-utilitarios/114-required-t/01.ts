// Tema: required t.
// Required elimina la opcionalidad de las propiedades del tipo recibido.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Person = {
    name?: string;
    age?: number;
};

type A = Required<Person>; // { name: string; age: number; }
