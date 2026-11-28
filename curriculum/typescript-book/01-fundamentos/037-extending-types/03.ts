// Tema:
// Un tipo puede combinarse o extender otro para exigir propiedades adicionales y reutilizar
// contratos.

type A = {
    a: number;
};
type B = {
    b: number;
};
type C = A & B;
