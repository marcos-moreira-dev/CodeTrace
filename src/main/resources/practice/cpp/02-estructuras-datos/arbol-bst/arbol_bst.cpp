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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\arbol-bst\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: arbol_bst.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   1 3 4 6 7 8 10 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
