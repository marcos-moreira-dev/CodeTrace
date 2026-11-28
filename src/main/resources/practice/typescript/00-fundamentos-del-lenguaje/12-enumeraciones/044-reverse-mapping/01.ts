// Tema: reverse mapping.
// Los enums numéricos pueden generar una relación inversa entre el valor numérico y el nombre de la constante.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

enum Grade {
    A = 90,
    B = 80,
    C = 70,
    F = 'fail',
}
