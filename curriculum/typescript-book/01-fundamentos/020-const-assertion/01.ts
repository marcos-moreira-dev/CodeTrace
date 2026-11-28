// Tema:
// as const conserva tipos literales y produce propiedades readonly y tuplas readonly en las
// expresiones correspondientes.

const v = {
    x: 3 as const,
};
v.x = 3;
