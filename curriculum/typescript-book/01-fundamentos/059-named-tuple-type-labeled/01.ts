// Tema:
// Las etiquetas de tupla documentan el papel de cada posición sin cambiar los valores de
// ejecución.

type T = string;
type Tuple1 = [T, T];
type Tuple2 = [a: T, b: T];
type Tuple3 = [a: T, T]; // Named Tuple plus Anonymous Tuple
