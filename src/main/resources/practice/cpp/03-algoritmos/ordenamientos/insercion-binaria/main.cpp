// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#include "InsercionBinaria.hpp"
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente Binary Insertion Sort: no equivale a std::lower_bound
 * solo, ya que el movimiento del vector continua siendo O(n²).
 * Un upperBound interno preserva empates en orden.
 */
int main(){
    std::vector<int> datos{5,1,3,3,2};
    insercionBinaria(datos);
    if(datos!=std::vector<int>({1,2,3,3,5}))
        throw std::logic_error("Insercion");
    std::vector<int> vacio;insercionBinaria(vacio);
    std::cout<<"Insercion binaria: [1, 2, 3, 3, 5]\n";
}
/*
 * Salida esperada:
 * Insercion binaria: [1, 2, 3, 3, 5]
 */
