// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#pragma once
#include <vector>
/**
 * lowerBound: primer a[i] >= objetivo. upperBound: primer a[i] >.
 * Precondicion arreglo ascendente; O(log n) tiempo y O(1) espacio.
 */
std::size_t lowerBound(const std::vector<int>& a,int objetivo);
std::size_t upperBound(const std::vector<int>& a,int objetivo);
