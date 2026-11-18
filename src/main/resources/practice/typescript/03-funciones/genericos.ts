function primero<T>(datos: readonly T[]): T | undefined {
  return datos[0];
}

const numero = primero<number>([4, 5, 6]);
const palabra = primero<string>(["uno", "dos"]);
console.log(numero, palabra);
