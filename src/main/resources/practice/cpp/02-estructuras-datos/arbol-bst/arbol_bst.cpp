// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "ArbolBST.hpp"

/** Conserva el invariante de orden en cada camino de inserción. */
void insertar(std::unique_ptr<Nodo>& nodo, int dato) {
    if (!nodo) { nodo = std::make_unique<Nodo>(dato); return; }
    if (dato < nodo->dato) insertar(nodo->izq, dato);
    else if (dato > nodo->dato) insertar(nodo->der, dato);
}
void inorden(const std::unique_ptr<Nodo>& nodo, std::vector<int>& salida) {
    if (!nodo) return;
    inorden(nodo->izq, salida);
    salida.push_back(nodo->dato);
    inorden(nodo->der, salida);
}
