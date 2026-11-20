// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 -Wall -Wextra ARCHIVO.cpp -o app.exe
// Ejecutar: ./app.exe

#include <algorithm>
#include <iostream>
#include <vector>
/**
 * Cliente de lower_bound: localiza el primer elemento no menor
 * que un objetivo. El rango debe estar ordenado previamente;
 * comprobar igualdad es necesario porque encontrar la posición
 * de inserción no equivale a encontrar el dato.
 * En vector, O(log n) comparaciones y espacio O(1).
 */
int main() {
    const std::vector<int> datos{1, 3, 3, 8, 13};
    auto it = std::lower_bound(datos.begin(), datos.end(), 3);
    if (it != datos.end() && *it == 3)
        std::cout << "Índice: " << (it - datos.begin()) << '\n';
}
