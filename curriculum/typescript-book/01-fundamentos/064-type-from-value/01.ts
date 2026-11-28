// Tema:
// typeof en una posición de tipo obtiene el tipo estático de un valor existente.

const x = 'x'; // TypeScript infers 'x' as a string literal with 'const' (immutable), but widens it to 'string' with 'let' (reassignable).
