// Tema: weak types.
// Un tipo formado por propiedades opcionales puede rechazar un objeto que no comparte ninguna de ellas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = {
    a?: string;
    b?: string;
};
