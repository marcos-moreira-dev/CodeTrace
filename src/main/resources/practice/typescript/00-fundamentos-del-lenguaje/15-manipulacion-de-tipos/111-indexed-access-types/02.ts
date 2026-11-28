// Tema: indexed access types.
// Un acceso indexado obtiene el tipo de una propiedad o conjunto de propiedades de otro tipo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type MyTuple = [string, number, boolean];
type MyType = MyTuple[2]; // boolean
