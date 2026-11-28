// Tema:
// Un tipo puede combinarse o extender otro para exigir propiedades adicionales y reutilizar
// contratos.

interface A {
    a: string;
}
interface B {
    b: string;
}
interface Y extends A, B {
    y: string;
}
