// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#pragma once
#include <memory>
#include <vector>

/**
 * Árbol BST administrado por propiedad exclusiva de sus hijos.
 * Insertar y buscar O(h), con h altura; peor O(n) sin balanceo.
 * El destructor de unique_ptr libera el árbol de forma recursiva.
 */
struct Nodo {
    int dato;
    std::unique_ptr<Nodo> izq, der;
    explicit Nodo(int x) : dato(x) {}
};

void insertar(std::unique_ptr<Nodo>& nodo, int dato);
void inorden(const std::unique_ptr<Nodo>& nodo, std::vector<int>& salida);
