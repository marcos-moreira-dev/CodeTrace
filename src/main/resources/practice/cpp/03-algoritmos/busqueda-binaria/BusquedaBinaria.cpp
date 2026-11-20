// CodeTrace | Referencia: C++20, g++ 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: g++ -std=c++20 -Wall -Wextra '.\03-algoritmos\busqueda-binaria\BusquedaBinaria.cpp' -o "$env:TEMP\codetrace-cpp.exe"
// Ejecutar: & "$env:TEMP\codetrace-cpp.exe"

#include <algorithm>
#include <iostream>
#include <iterator>
#include <vector>
int main() {
    const std::vector<int> numeros = {2, 4, 8, 12, 16};
    auto posicion = std::lower_bound(numeros.begin(), numeros.end(), 12);
    if (posicion != numeros.end() && *posicion == 12)
        std::cout << std::distance(numeros.begin(), posicion) << '\n';
    return 0;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\busqueda-binaria\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
