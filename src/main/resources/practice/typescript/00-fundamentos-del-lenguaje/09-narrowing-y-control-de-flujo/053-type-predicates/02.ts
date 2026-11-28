// Tema: type predicates.
// Un retorno de la forma valor is Tipo permite expresar una comprobación que reduce el tipo del argumento.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const nums = [1, null, 2].filter(x => x !== null);
