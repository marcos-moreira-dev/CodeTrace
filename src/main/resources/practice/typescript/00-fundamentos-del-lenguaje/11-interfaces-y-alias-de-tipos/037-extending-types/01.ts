// Tema: extending types.
// Un tipo puede combinarse o extender otro para exigir propiedades adicionales y reutilizar contratos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface X {
    a: string;
}
interface Y extends X {
    b: string;
}
