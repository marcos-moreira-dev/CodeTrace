// Tema:
// Dos tipos son compatibles por sus miembros y no solo por el nombre de su declaración.

type X = {
    a: string;
};
type Y = {
    a: string;
};
const x: X = { a: 'a' };
const y: Y = x; // Valid
