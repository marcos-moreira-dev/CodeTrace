// Tema: union type.
// Una unión permite que un valor pertenezca a cualquiera de sus alternativas; solo se usan miembros seguros sin reducir el tipo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

let x: string | number;
x = 'hello'; // Válido
x = 123; // Válido
