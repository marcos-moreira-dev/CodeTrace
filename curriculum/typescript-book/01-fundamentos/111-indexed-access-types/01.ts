// Tema:
// Un acceso indexado obtiene el tipo de una propiedad o conjunto de propiedades de otro tipo.

type Person = {
    name: string;
    age: number;
};

type AgeType = Person['age']; // number
