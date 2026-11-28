// Tema: recursive types.
// Un tipo recursivo puede referirse a sí mismo para describir estructuras anidadas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type ListNode<T> = {
    data: T;
    next: ListNode<T> | undefined;
};
