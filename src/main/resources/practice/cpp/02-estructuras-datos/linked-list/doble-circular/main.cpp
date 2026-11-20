// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "ListaDobleCircular.hpp"
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente lista doble circular, variant de Linked List.
 *
 * Los enlaces prev/next forman un ciclo, pero la propiedad pertenece
 * a un contenedor de unique_ptr. Es la diferencia entre topología
 * y ciclo de vida, importante para C++ y sus destructores.
 */
int main(){
    ListaDobleCircular<int> l;
    for(int x:{2,3,5})l.agregar(x);
    if(l.valores()!=std::vector<int>({2,3,5}) ||
       l.valores(true)!=std::vector<int>({5,3,2}))
        throw std::logic_error("Doble circular");
    std::cout<<"Doble circular OK\n";
}

/*
 * Salida esperada de consola:
 *   Doble circular OK\n
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: las aserciones se cumplen y el proceso termina sin error.
 */
