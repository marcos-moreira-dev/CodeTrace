// Tema: Emisión y target
// El acceso opcional y ?? se transforman para un target anterior; las anotaciones se eliminan. target no instala polyfills.
// Desde la carpeta de este ejercicio:
// Compilar: tsc -p .
// Ejecutar: node emision.js
// Salida esperada:
// Invitado

function nombre(persona?: { nombre: string }): string {
    return persona?.nombre ?? 'Invitado';
}
console.log(nombre());
