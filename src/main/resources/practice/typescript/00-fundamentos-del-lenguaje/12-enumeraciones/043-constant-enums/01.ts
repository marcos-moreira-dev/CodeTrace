// Tema: constant enums.
// Un const enum permite que las referencias a sus miembros se sustituyan por valores constantes según la configuración de compilación.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// EN

const enum Language {
    English = 'EN',
    Spanish = 'ES',
}
console.log(Language.English);
