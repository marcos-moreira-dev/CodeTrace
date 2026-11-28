// Tema:
// Las anotaciones expresan el tipo de variables, parámetros y retornos para detectar usos
// incompatibles.

const sum = (a = 10, b: number): number => a + b;
