// Tema:
// Omit elimina del tipo las propiedades correspondientes a las claves indicadas.

type Product = {
    name: string;
    price: number;
};

type Name = Omit<Product, 'price'>; // { name: string; }
