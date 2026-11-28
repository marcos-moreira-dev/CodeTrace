// Tema:
// using registra un recurso para liberar automáticamente cuando se abandona su ámbito si
// implementa el protocolo de disposición.

{
    using j = getA(),
        y = getB();
    using k = getC();
} // disposes `C`, then `B`, then `A`.
