// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar: g++ -std=c++20 -Wall -Wextra '.\03-algoritmos\grafos\topologico\kahn.cpp' -o "$env:TEMP\ct-cpp-graph.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-graph.exe"

#include <iostream>
#include <map>
#include <queue>
#include <stdexcept>
#include <string>
#include <vector>

/**
 * Cliente de orden topológico (Kahn) sobre un DAG.
 *
 * La arista A->B expresa dependencia: A debe finalizar antes que B.
 * El grado de entrada de B cuenta dependencias pendientes. Se atienden
 * primero vértices sin dependencias. Un ciclo deja vértices sin procesar:
 * se rechaza la ordenación, no se inventa un resultado parcial.
 *
 * O((V+E) log V) tiempo usando std::map (búsquedas logarítmicas),
 * y O(V+E) memoria. Con tablas hash el costo esperado se acerca a O(V+E).
 */
using Grafo = std::map<std::string, std::vector<std::string>>;

std::vector<std::string> ordenar(const Grafo& grafo) {
    std::map<std::string, int> entradas;
    for (const auto& [v, vecinos] : grafo) {
        entradas.try_emplace(v, 0);
        for (const auto& w : vecinos) entradas.try_emplace(w, 0);
    }
    for (const auto& [v, vecinos] : grafo)
        for (const auto& w : vecinos) entradas[w]++;
    std::queue<std::string> cola;
    for (const auto& [v, grado] : entradas)
        if (grado == 0) cola.push(v);
    std::vector<std::string> orden;
    while (!cola.empty()) {
        std::string v = cola.front(); cola.pop();
        orden.push_back(v);
        if (auto it = grafo.find(v); it != grafo.end())
            for (const auto& w : it->second)
                if (--entradas[w] == 0) cola.push(w);
    }
    if (orden.size() != entradas.size())
        throw std::logic_error("Dependencias cíclicas");
    return orden;
}
int main() {
    Grafo g{
        {"Fuentes", {"Compilar"}},
        {"Compilar", {"Pruebas", "Empaquetar"}},
        {"Pruebas", {"Desplegar"}},
        {"Empaquetar", {"Desplegar"}},
        {"Desplegar", {}}
    };
    for (const auto& paso : ordenar(g)) std::cout << paso << '\n';
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\grafos\topologico\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Fuentes
 *   Compilar
 *   Pruebas
 *   Empaquetar
 *   Desplegar
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
