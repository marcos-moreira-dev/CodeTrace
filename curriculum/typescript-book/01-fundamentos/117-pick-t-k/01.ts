// Tema:
// Pick selecciona un subconjunto de propiedades de un tipo existente.

type Product = {
    name: string;
    price: number;
};

type Price = Pick<Product, 'price'>; // { price: number; }
