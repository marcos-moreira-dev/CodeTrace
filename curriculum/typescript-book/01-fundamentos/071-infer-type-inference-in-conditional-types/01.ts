// Tema:
// infer introduce una variable de tipo que se deduce de una estructura comparada dentro de un
// tipo condicional.

type ElementType<T> = T extends (infer U)[] ? U : never;
type Numbers = ElementType<number[]>; // number
type Strings = ElementType<string[]>; // string
