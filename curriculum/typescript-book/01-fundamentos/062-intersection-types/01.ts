// Tema:
// Una intersección exige que un valor satisfaga simultáneamente los contratos que la componen.

type X = {
    a: string;
};

type Y = {
    b: string;
};

type J = X & Y; // Intersection

const j: J = {
    a: 'a',
    b: 'b',
};
