// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 -Wall -Wextra ARCHIVO.cpp -o app.exe
// Ejecutar: ./app.exe

#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
/**
 * Cliente de STL, iteradores y lambda de comparación.
 * std::sort recibe una función que define qué elemento precede
 * a otro. No es filtrado: todos los elementos permanecen.
 * En C++20 se garantizan O(n log n) comparaciones.
 */
struct Producto { std::string id; double precio; };
int main() {
    std::vector<Producto> datos{{"B", 0.5}, {"C", 0.2}, {"A", 0.5}};
    std::sort(datos.begin(), datos.end(), [](const Producto& a, const Producto& b) {
        return a.precio < b.precio || (a.precio == b.precio && a.id < b.id);
    });
    for (const auto& p : datos) std::cout << p.id << ": " << p.precio << '\n';
}
