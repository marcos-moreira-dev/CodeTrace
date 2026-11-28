// Tema:
// Un tipo puede combinarse o extender otro para exigir propiedades adicionales y reutilizar
// contratos.

interface X {
    a: string;
}
interface Y extends X {
    b: string;
}
