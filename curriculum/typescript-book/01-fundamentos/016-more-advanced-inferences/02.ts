// Tema:
// La inferencia puede obtener un tipo común, una unión o un tipo contextual según las
// expresiones y el contexto de uso.

let x = [new RegExp('x'), new Date()]; // Type inferred is: (RegExp | Date)[]
