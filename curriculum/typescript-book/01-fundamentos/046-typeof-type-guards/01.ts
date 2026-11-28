// Tema:
// typeof permite discriminar tipos primitivos mediante una comprobación que existe en
// JavaScript.

const fn = (x: number | string) => {
    if (typeof x === 'number') {
        return x + 1; // x is number
    }
    return -1;
};
