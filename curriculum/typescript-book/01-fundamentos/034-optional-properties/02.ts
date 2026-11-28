// Tema:
// El signo ? indica que una propiedad puede estar ausente; su lectura debe considerar
// undefined.

type X = {
    a: number;
    b?: number;
};
const x = ({ a, b = 100 }: X) => a + b;
