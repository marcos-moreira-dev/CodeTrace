// Tema:
// Un tipo recursivo puede referirse a sí mismo para describir estructuras anidadas.

type ListNode<T> = {
    data: T;
    next: ListNode<T> | undefined;
};
