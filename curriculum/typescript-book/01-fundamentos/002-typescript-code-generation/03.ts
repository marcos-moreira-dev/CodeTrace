// Tema:
// La comprobación de tipos y la emisión de JavaScript son procesos distintos. Las interfaces y
// anotaciones se borran, mientras que las clases y propiedades reales pueden existir en
// ejecución.

interface Animal {
    name: string;
}
interface Dog extends Animal {
    bark: () => void;
}
interface Cat extends Animal {
    meow: () => void;
}
const makeNoise = (animal: Animal) => {
    if (animal instanceof Dog) {
        // 'Dog' only refers to a type, but is being used as a value here.
        // ...
    }
};
