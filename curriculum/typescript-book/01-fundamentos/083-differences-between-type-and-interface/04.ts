// Tema:
// interface y type se solapan para objetos, pero difieren en fusión, uniones y otros usos del
// sistema de tipos.

type Department = 'dep-x' | 'dep-y'; // Union

type Person = {
    name: string;
    age: number;
};

type Employee = {
    id: number;
    department: Department;
};

type EmployeeInfo = Person & Employee; // Intersection
