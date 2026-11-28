// Tema:
// Los valores predeterminados permiten inicializar parámetros o declaraciones cuando no se
// proporciona el valor correspondiente.

function greet(name: string = 'Anonymous'): void {
    console.log(`Hello, ${name}!`);
}
greet(); // Hello, Anonymous!
greet('John'); // Hello, John!
