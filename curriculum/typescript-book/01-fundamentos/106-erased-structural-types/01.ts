// Tema:
// Los tipos estructurales se comprueban estáticamente y no proporcionan por sí mismos una
// identidad de tipo en ejecución.

type NameProp1 = {
    prop1: string;
};

function log(x: NameProp1) {
    console.log(x.prop1);
}

const obj = {
    prop2: 123,
    prop1: 'Origin',
};

log(obj); // Valid
