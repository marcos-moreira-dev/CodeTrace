// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 main.cpp estrategias.cpp -o app.exe
// Ejecutar: ./app.exe

#include "estrategia.hpp"
#include <iostream>
/**
 * Cliente de Strategy y compilación separada con C++20.
 * Una estrategia define cómo calcular el total, otra puede sustituirla
 * sin modificar el cliente. La interfaz es un contrato, la fábrica
 * entrega un unique_ptr y el destructor virtual permite liberar
 * correctamente el objeto concreto.
 *
 * Desde la carpeta: g++ -std=c++20 main.cpp estrategias.cpp -o app.exe
 * Ejecutar: ./app.exe
 */
int main() {
    auto a = normal(), b = descuento();
    std::cout << a->total(100) << '\n' << b->total(100) << '\n';
}
