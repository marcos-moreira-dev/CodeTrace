// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 -Wall -Wextra ARCHIVO.cpp -o app.exe
// Ejecutar: ./app.exe

#include <functional>
#include <iostream>
#include <queue>
#include <vector>
/**
 * Cliente de cola de prioridad: heap de la biblioteca estándar.
 * greater<> hace que el menor entero salga primero. Insertar
 * y extraer cuestan O(log n), consultar la raíz O(1).
 * Compárese con la implementación manual de heap en Java.
 */
int main() {
    std::priority_queue<int, std::vector<int>, std::greater<int>> cola;
    for (int p : {7, 2, 9, 1, 5}) cola.push(p);
    while (!cola.empty()) {
        std::cout << cola.top() << '\n';
        cola.pop();
    }
}
