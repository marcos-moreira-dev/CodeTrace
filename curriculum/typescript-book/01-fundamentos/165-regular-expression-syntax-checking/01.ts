// Tema:
// El compilador puede detectar ciertos errores sintácticos en literales de expresiones
// regulares, antes de ejecutar el programa.

let r = /(a)\2/; // Error: This backreference refers to a group that does not exist.
