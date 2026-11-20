// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#include "LimitesOrdenados.hpp"
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente Lower/Upper Bound: posiciones de insercion en arreglo
 * ya ordenado, incluyendo ausentes y repetidos. En C++ std::lower_bound
 * y std::upper_bound de <algorithm> implementan una API equivalente;
 * aqui se hace manual para estudiar el invariante semiabierto [lo,hi).
 */
int main(){
    const std::vector<int> a{1,3,3,3,5,9};
    auto lo=lowerBound(a,3),hi=upperBound(a,3),pos=lowerBound(a,4);
    if(lo!=1||hi!=4||pos!=4||upperBound(a,4)!=4)
        throw std::logic_error("Rangos");
    std::cout<<"Valor 3: ["<<lo<<", "<<hi<<")\n";
    std::cout<<"Insertar 4 en indice: "<<pos<<'\n';
}
/*
 * Salida esperada:
 * Valor 3: [1, 4)
 * Insertar 4 en indice: 4
 */
