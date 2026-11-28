// Tema: nonnullable t.
// NonNullable excluye null y undefined de un tipo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Union = 'a' | null | undefined | 'b';
type MyType = NonNullable<Union>; // 'a' | 'b'
