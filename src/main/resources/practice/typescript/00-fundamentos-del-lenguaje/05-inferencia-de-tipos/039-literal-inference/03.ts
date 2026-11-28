// Tema: literal inference.
// La aserción const sobre una propiedad conserva el literal sin congelar todo el objeto.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

let o = {
    x: 'a' as const,
};
