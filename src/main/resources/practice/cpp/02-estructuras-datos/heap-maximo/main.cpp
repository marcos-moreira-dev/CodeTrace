// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "HeapMaximo.hpp"
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente Max heap usando adaptador priority_queue de la STL.
 * Es la ventaja idiomática de C++: no reconstruir siempre una
 * estructura probada. Al extraer sucesivamente se obtiene orden
 * descendente; el heap subyacente no está globalmente ordenado.
 */
int main(){
    HeapMaximo h;
    for(int x:{4,1,9,7,2})h.insertar(x);
    for(int x:{9,7,4,2,1})if(h.extraer()!=x)throw std::logic_error("Heap");
    std::cout<<"Max heap correcto\n";
}
