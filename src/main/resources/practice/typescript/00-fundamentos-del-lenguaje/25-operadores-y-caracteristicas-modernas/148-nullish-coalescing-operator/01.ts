// Tema: nullish coalescing operator.
// El operador ?? solo sustituye null y undefined. Se usan parámetros para evitar diagnósticos de expresiones constantes.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// foo
// 1
// 0

function elegir(valor: string | number | null | undefined, alternativa: string): string | number {
    return valor ?? alternativa;
}
console.log(elegir(null,"foo"));
console.log(elegir(1,"baz"));
console.log(elegir(0,"baz"));
