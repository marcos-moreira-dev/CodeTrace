// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "DequeCurso.hpp"
#include <iostream>
#include <stdexcept>
#include <string>

/**
 * Cliente Deque: una estructura puede comportarse como pila y cola.
 * C++ proporciona std::deque sin forzar una lista doble manual;
 * esta se explica en linked-list/doble. Es idiomático aprovechar STL
 * cuando lo que se estudia es el contrato y no la implementación.
 */
int main(){
    DequeCurso<std::string> d;
    d.insertarPrimero("B");d.insertarPrimero("A");d.insertarUltimo("C");
    if(d.quitarPrimero()!="A"||d.quitarUltimo()!="C"||
       d.quitarUltimo()!="B")throw std::logic_error("Deque");
    std::cout<<"Deque correcta\n";
}
