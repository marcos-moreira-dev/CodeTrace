// Tema: extending types.
// Un tipo puede combinarse o extender otro para exigir propiedades adicionales y reutilizar contratos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface A {
    a: string;
}
interface B {
    b: string;
}
interface Y extends A, B {
    y: string;
}
