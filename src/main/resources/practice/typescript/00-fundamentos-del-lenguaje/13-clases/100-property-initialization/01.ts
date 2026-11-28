// Tema: property initialization.
// El compilador puede comprobar que las propiedades requeridas de una clase estén inicializadas antes de usarlas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

class MyClass {
    property1: string = 'default value';
    property2: number = 42;
}
