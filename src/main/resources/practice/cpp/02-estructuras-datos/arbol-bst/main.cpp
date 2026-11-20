// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "ArbolBST.hpp"
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente de un BST con nodos que se poseen recursivamente.
 *
 * El recorrido inorden produce una secuencia ascendente por
 * la relación menor/izquierdo, mayor/derecho. El nodo es un
 * objeto y la propiedad se expresa por unique_ptr, no new/delete.
 * Para inserciones ya ordenadas este árbol puede degenerarse.
 */
int main() {
    std::unique_ptr<Nodo> raiz;
    for (int x : {8, 3, 10, 1, 6, 4, 7}) insertar(raiz, x);
    std::vector<int> resultado;
    inorden(raiz, resultado);
    if (resultado != std::vector<int>({1, 3, 4, 6, 7, 8, 10}))
        throw std::logic_error("Orden inorden incorrecto");
    for (int x : resultado) std::cout << x << ' ';
    std::cout << '\n';
}
