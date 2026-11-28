// Tema: extending types.
// Un tipo puede combinarse o extender otro para exigir propiedades adicionales y reutilizar contratos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type A = {
    a: number;
};
type B = {
    b: number;
};
type C = A & B;
