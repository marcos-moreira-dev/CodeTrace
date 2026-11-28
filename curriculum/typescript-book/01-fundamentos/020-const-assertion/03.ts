// Tema:
// as const conserva tipos literales y produce propiedades readonly y tuplas readonly en las
// expresiones correspondientes.

const x = [1, 2, 3]; // number[]
const y = [1, 2, 3] as const; // Tuple of readonly [1, 2, 3]
