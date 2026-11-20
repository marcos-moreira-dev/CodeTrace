// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 -Wall -Wextra 03-algoritmos/busqueda-binaria-stl/busqueda_binaria_stl.cpp -o app.exe
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\busqueda-binaria-stl\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Índice: 1
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
