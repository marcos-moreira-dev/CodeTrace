// Tema: auto accessors in classes.
// accessor declara automáticamente el almacenamiento y los accesores de una propiedad de clase.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

class Animal {
    accessor name: string;

    constructor(name: string) {
        this.name = name;
    }
}
