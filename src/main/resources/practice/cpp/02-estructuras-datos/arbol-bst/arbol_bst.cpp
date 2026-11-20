// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\02-estructuras-datos\arbol_bst.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <iostream>
#include <memory>
#include <vector>

/**
 * Cliente de BST con nodos administrados por unique_ptr.
 *
 * Un nodo posee sus hijos, por lo que liberar la raíz libera
 * recursivamente el árbol. Los valores menores van a la izquierda;
 * los mayores a la derecha. Insertar y buscar cuestan O(h), donde
 * h es altura. Este BST NO se autobalancea (peor caso O(n)).
 */
struct Nodo {
    int dato;
    std::unique_ptr<Nodo> izq, der;
    explicit Nodo(int d) : dato(d) {}
};
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
int main() {
    std::unique_ptr<Nodo> raiz;
    for (int x : {8, 3, 10, 1, 6, 4, 7}) insertar(raiz, x);
    std::vector<int> resultado;
    inorden(raiz, resultado);
    for (int x : resultado) std::cout << x << ' ';
    std::cout << '\n';
}
