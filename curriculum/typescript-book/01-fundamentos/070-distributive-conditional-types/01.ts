// Tema:
// Un condicional con un parámetro de tipo desnudo puede distribuirse sobre los miembros de una
// unión.

type Nullable<T> = T extends any ? T | null : never;
type NumberOrBool = number | boolean;
type NullableNumberOrBool = Nullable<NumberOrBool>; // number | boolean | null
