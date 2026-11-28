// Tema: ambient enum.
// La declaración describe el contrato; no genera un objeto EstadoExterno en JavaScript.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 consulta.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

/// <reference path="./ambient_enum.d.ts" />
type Estado = EstadoExterno;
declare const estado: Estado;
const codigo: number = estado;
