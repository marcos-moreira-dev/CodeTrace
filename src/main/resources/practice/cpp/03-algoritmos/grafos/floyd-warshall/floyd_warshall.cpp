// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar: g++ -std=c++20 -Wall -Wextra '.\03-algoritmos\grafos\floyd-warshall\floyd_warshall.cpp' -o "$env:TEMP\ct-cpp-graph.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-graph.exe"

#include <algorithm>
#include <iostream>
#include <limits>
#include <stdexcept>
#include <vector>

/**
 * Cliente Floyd-Warshall: caminos mínimos de TODOS los pares.
 *
 * La matriz representa las distancias actuales. En cada etapa k se
 * comprueba si usar el vértice k mejora el camino i->j. Hay tres
 * bucles sobre V: tiempo O(V³), memoria O(V²). Admite aristas de
 * peso negativo pero no ciclos negativos para resultados finitos.
 */
using Matriz = std::vector<std::vector<double>>;

Matriz floyd(Matriz d) {
    const size_t n = d.size();
    for (const auto& fila : d)
        if (fila.size() != n) throw std::invalid_argument("Matriz cuadrada");
    for (size_t k = 0; k < n; k++)
        for (size_t i = 0; i < n; i++)
            for (size_t j = 0; j < n; j++) {
                if (d[i][k] == std::numeric_limits<double>::infinity()
                    || d[k][j] == std::numeric_limits<double>::infinity()) continue;
                d[i][j] = std::min(d[i][j], d[i][k] + d[k][j]);
            }
    for (size_t i = 0; i < n; i++)
        if (d[i][i] < 0) throw std::logic_error("Ciclo negativo");
    return d;
}
int main() {
    const double inf = std::numeric_limits<double>::infinity();
    Matriz entrada{{0,4,10,inf}, {inf,0,-2,inf},
                   {inf,inf,0,3}, {inf,inf,inf,0}};
    const auto d = floyd(entrada);
    std::cout << "Distancia 0->3: " << d[0][3] << '\n';
    if (d[0][3] != 5) throw std::logic_error("Error de recorrido");
}
