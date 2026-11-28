// Tema: type assertion.
// as comunica al compilador un tipo que el programador afirma conocer. No valida ni transforma el valor en ejecución.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: comprobación de tipos; ejecutar solo en el entorno indicado, no en Node por defecto.

const myInput = document.getElementById('my_input') as HTMLInputElement;
