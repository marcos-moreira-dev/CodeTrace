// Tema: numeric enums.
// Los miembros numéricos de un enum pueden recibir valores explícitos o incrementarse automáticamente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

enum Size {
    Small, // value starts from 0
    Medium,
    Large,
}
