// Tema: type indexing.
// El acceso indexado obtiene el tipo de una propiedad a partir de un tipo de objeto y su clave.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// a

type Dictionary<T> = {
    [key: string]: T;
};
const myDict: Dictionary<string> = { a: 'a', b: 'b' };
console.log(myDict['a']); // Returns a
