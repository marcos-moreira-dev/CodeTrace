// Tema: overloads.
// Las sobrecargas describen varias firmas de llamada compatibles con una implementación.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

// Sobrecargas
function sayHi(name: string): string;
function sayHi(names: string[]): string[];

// Implementación
function sayHi(name: unknown): unknown {
    if (typeof name === 'string') {
        return `Hi, ${name}!`;
    } else if (Array.isArray(name)) {
        return name.map(name => `Hi, ${name}!`);
    }
    throw new Error('Invalid value');
}

sayHi('xx'); // Válido
sayHi(['aa', 'bb']); // Válido
