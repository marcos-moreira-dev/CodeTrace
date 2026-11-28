// Tema:
// readonly impide modificar la propiedad a través del tipo estático correspondiente; no
// garantiza inmutabilidad profunda.

interface Y {
    readonly a: number;
}

type X = {
    readonly a: number;
};

type J = Readonly<{
    a: number;
}>;

type K = {
    readonly [index: number]: string;
};
