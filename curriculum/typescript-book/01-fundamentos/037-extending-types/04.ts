// Tema:
// Un tipo puede combinarse o extender otro para exigir propiedades adicionales y reutilizar
// contratos.

type A = {
    a: string;
};
interface B extends A {
    b: string;
}
