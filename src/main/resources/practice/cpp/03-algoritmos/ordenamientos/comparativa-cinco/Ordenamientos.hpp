// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#pragma once
#include <vector>

/**
 * Cinco ordenamientos en arreglos de enteros (std::vector<int>).
 *
 * Insertion/Bubble: mejor O(n), peor O(n^2), estables, O(1) aux.
 * Merge: O(n log n), estable si izquierda primero en empate, O(n) aux.
 * Heap: O(n log n), no estable, O(1) aux.
 * Quick pivote final: promedio O(n log n), peor O(n^2), no estable.
 * Todos actuan in-place sobre el vector recibido; no usan std::sort.
 */
namespace Ordenamientos {
void insercion(std::vector<int>& a);
void burbuja(std::vector<int>& a);
void mezcla(std::vector<int>& a);
void heap(std::vector<int>& a);
void rapido(std::vector<int>& a);
}
