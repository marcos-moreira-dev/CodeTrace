// Tema: numeric enums.
// Los miembros numéricos de un enum pueden recibir valores explícitos o incrementarse automáticamente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// 11

enum Size {
    Small = 10,
    Medium,
    Large,
}
console.log(Size.Medium); // 11
