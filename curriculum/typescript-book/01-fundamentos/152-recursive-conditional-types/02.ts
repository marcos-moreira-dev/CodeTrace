// Tema:
// Un condicional recursivo transforma estructuras de tipo mediante llamadas recursivas sujetas
// a límites del compilador.

type Json = string | number | boolean | null | Json[] | { [key: string]: Json };

const data: Json = {
    prop1: true,
    prop2: 'prop2',
    prop3: {
        prop4: [],
    },
};
