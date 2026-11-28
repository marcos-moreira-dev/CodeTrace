// Tema: optional properties.
// El signo ? indica que una propiedad puede estar ausente; su lectura debe considerar undefined.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = {
    a: number;
    b?: number;
};
const x = ({ a, b = 100 }: X) => a + b;
