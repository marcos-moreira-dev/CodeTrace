// Tema:
// as comunica al compilador un tipo que el programador afirma conocer. No valida ni transforma
// el valor en ejecución.

type J<Type> = {
    [Property in keyof Type as `prefix_${string &
        Property}`]: () => Type[Property];
};
type X = {
    a: string;
    b: number;
};
type Y = J<X>;
