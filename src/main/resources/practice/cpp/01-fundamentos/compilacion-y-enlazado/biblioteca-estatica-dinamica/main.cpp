// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o WSL
// Compilar: sh compilar.sh
#include "operaciones.hpp"
#include <iostream>

/**
 * Cliente de biblioteca estática (.a) y compartida (.so) C++20.
 *
 * Un .hpp declara la API pública, el .cpp define las funciones y
 * main.cpp únicamente las consume. La declaración puede incluirse
 * múltiples veces, pero cada función no-inline tiene UNA definición
 * (One Definition Rule). El enlazador une unidades de traducción.
 *
 * Biblioteca estática: ar empaqueta .o y el enlazado incorpora los
 * símbolos necesarios. Biblioteca compartida: g++ -shared -fPIC
 * produce una .so que un cargador localizará al ejecutar.
 *
 * A diferencia de C, el enlazado C++ puede codificar nombres,
 * sobrecargas y namespaces mediante name mangling. La ABI binaria
 * estable entre compiladores/versiones NO se garantiza por este ejemplo;
 * exportar una API C con extern "C" sería una decisión adicional.
 *
 * Las dos variantes devuelven el mismo resultado y se compilan en
 * un directorio temporal de una copia, sin modificar CodeTrace.
 */
int main() {
    const int s = didactico::sumar(3, 4);
    const int p = didactico::multiplicar(3, 4);
    std::cout << "Suma=" << s << ", producto=" << p << '\n';
    return (s == 7 && p == 12) ? 0 : 1;
}

/*
 * Salida esperada de consola:
 *   Suma=
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: las aserciones se cumplen y el proceso termina sin error.
 */
