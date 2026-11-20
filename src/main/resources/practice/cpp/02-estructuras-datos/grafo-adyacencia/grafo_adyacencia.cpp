// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\02-estructuras-datos\grafo-adyacencia\grafo_adyacencia.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <iostream>
#include <map>
#include <string>
#include <vector>

/**
 * Cliente de representación de un grafo mediante listas de adyacencia.
 *
 * Un map almacena cada vértice junto a sus vecinos; no hay matriz
 * VxV, por lo que el espacio para la representación es O(V+E).
 * La arista dirigida A->B no implica una relación en sentido inverso.
 */
using Grafo = std::map<std::string, std::vector<std::string>>;
int main() {
    Grafo grafo;
    grafo["A"] = {"B", "C"};
    grafo["B"] = {"D"};
    grafo["C"] = {};
    grafo["D"] = {};
    for (const auto& [vertice, vecinos] : grafo) {
        std::cout << vertice << " -> ";
        for (const auto& vecino : vecinos) std::cout << vecino << ' ';
        std::cout << '\n';
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\grafo-adyacencia\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   A -> B C 
 *   B -> D 
 *   C -> 
 *   D -> 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
