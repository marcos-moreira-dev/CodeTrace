// Tema:
// Las declaraciones de interfaz pueden fusionarse o extenderse; los alias de tipo no admiten
// fusión de declaraciones.

interface X {
    a: string;
}

interface X {
    b: number;
}

const person: X = {
    a: 'a',
    b: 7,
};
