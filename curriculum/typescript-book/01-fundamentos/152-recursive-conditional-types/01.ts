// Tema:
// Un condicional recursivo transforma estructuras de tipo mediante llamadas recursivas sujetas
// a límites del compilador.

type CheckNumber<T> = T extends number ? 'Number' : 'Not a number';
type A = CheckNumber<123>; // 'Number'
type B = CheckNumber<'abc'>; // 'Not a number'
