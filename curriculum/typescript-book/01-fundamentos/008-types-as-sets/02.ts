// Tema:
// Un tipo representa un conjunto de valores. La unión reúne alternativas; la intersección
// exige satisfacer ambos tipos.

type X = {
    a: string;
};
type Y = {
    a: string;
    b: string;
};
type XY = X & Y;
const r: XY = { a: 'a' }; // Invalid
const j: XY = { a: 'a', b: 'b' }; // Valid
