// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "ListaSimple.hpp"
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente Linked List simple: nodos enlazados por propiedad RAII.
 *
 * A diferencia de Java, C++ expresa la propiedad exclusiva mediante
 * unique_ptr y la liberación se hace al destruir la lista. Agregar al
 * inicio O(1); recorrer O(n). No utilizar new/delete manual.
 */
int main() {
    ListaSimple<int> l;
    for(int x:{1,2,3})l.agregarPrimero(x);
    if(l.valores()!=std::vector<int>({3,2,1}))throw std::logic_error("Lista");
    std::cout<<"Linked list simple OK\n";
}
