// Tema:
// El acceso indexado obtiene el tipo de una propiedad a partir de un tipo de objeto y su
// clave.

type Dictionary<T> = {
    [key: string]: T;
};
const myDict: Dictionary<string> = { a: 'a', b: 'b' };
console.log(myDict['a']); // Returns a
