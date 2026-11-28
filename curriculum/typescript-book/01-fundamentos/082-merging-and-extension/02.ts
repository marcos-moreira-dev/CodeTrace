// Tema:
// Las declaraciones de interfaz pueden fusionarse o extenderse; los alias de tipo no admiten
// fusión de declaraciones.

interface Animal {
    name: string;
    eat(): void;
}

interface Bird extends Animal {
    sing(): void;
}

const dog: Bird = {
    name: 'Bird 1',
    eat() {
        console.log('Eating');
    },
    sing() {
        console.log('Singing');
    },
};
