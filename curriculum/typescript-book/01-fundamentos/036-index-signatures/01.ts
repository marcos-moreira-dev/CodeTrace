// Tema:
// Una firma de índice describe el tipo de las propiedades accesibles por claves de un tipo
// determinado.

type K = {
    [name: string | number]: string;
};
const k: K = { x: 'x', 1: 'b' };
console.log(k['x']);
console.log(k[1]);
console.log(k['1']); // Same result as k[1]
