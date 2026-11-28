// Tema:
// Una asignación modifica el tipo observado por el análisis de flujo sin cambiar el contrato
// declarado de la variable.

let value: string | number;
value = 'hello';
if (typeof value === 'string') {
    console.log(value.toUpperCase());
}
value = 42;
if (typeof value === 'number') {
    console.log(value.toFixed(2));
}
