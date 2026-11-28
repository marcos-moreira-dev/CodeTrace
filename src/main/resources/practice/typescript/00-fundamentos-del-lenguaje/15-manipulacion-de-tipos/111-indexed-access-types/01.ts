// Tema: indexed access types.
// Un acceso indexado obtiene el tipo de una propiedad o conjunto de propiedades de otro tipo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Person = {
    name: string;
    age: number;
};

type AgeType = Person['age']; // number
