// Tema: more advanced inferences.
// La inferencia puede obtener un tipo común, una unión o un tipo contextual según las expresiones y el contexto de uso.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Salida esperada: comprobación de tipos; ejecutar solo en el entorno indicado, no en Node por defecto.

window.addEventListener('click', function (e) {}); // The inferred type of e is MouseEvent
