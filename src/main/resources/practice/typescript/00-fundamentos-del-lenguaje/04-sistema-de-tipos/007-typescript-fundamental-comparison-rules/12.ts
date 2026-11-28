// Tema: typescript fundamental comparison rules.
// Los miembros de enumeraciones diferentes no son comparables sin conversión explícita.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 12.ts
// Diagnóstico esperado: TS2367; no ejecutar.

enum X {
    A,
    B,
}
enum Y {
    A,
    B,
    C,
}
const xa: number = X.A; // Válido
const ya: Y = 0; // Válido
X.A === Y.A; // Inválido
