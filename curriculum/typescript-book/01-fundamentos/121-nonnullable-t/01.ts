// Tema:
// NonNullable excluye null y undefined de un tipo.

type Union = 'a' | null | undefined | 'b';
type MyType = NonNullable<Union>; // 'a' | 'b'
