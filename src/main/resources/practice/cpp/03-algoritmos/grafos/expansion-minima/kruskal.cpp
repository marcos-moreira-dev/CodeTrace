// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar: g++ -std=c++20 -Wall -Wextra '.\03-algoritmos\grafos\expansion-minima\kruskal.cpp' -o "$env:TEMP\ct-cpp-graph.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-graph.exe"

#include <algorithm>
#include <iostream>
#include <numeric>
#include <stdexcept>
#include <vector>

/**
 * Cliente de Kruskal y DSU en C++20.
 *
 * Se ordenan las aristas de un grafo NO dirigido. Union-Find evita
 * ciclos al comprobar si dos extremos pertenecen a componentes
 * distintas. El árbol minimiza el costo total de conectar vértices,
 * no necesariamente el costo del camino entre cada par.
 *
 * O(E log E) tiempo por ordenar, O(V+E) memoria.
 */
struct Arista { int desde, hasta, costo; };

class UnionFind {
    std::vector<int> padre, rango;
public:
    explicit UnionFind(int n) : padre(n), rango(n, 0) {
        std::iota(padre.begin(), padre.end(), 0);
    }
    int encontrar(int v) {
        return padre[v] == v ? v : padre[v] = encontrar(padre[v]);
    }
    bool unir(int a, int b) {
        a = encontrar(a);
        b = encontrar(b);
        if (a == b) return false;
        if (rango[a] < rango[b]) std::swap(a, b);
        padre[b] = a;
        if (rango[a] == rango[b]) rango[a]++;
        return true;
    }
};

int kruskal(int vertices, std::vector<Arista> aristas) {
    if (vertices < 1) throw std::invalid_argument("Vértices");
    for (const auto& e : aristas)
        if (e.desde < 0 || e.hasta < 0 || e.desde >= vertices
            || e.hasta >= vertices || e.desde == e.hasta)
            throw std::invalid_argument("Arista");
    std::sort(aristas.begin(), aristas.end(),
              [](const Arista& a, const Arista& b) { return a.costo < b.costo; });
    UnionFind uf(vertices);
    int total = 0;
    for (const auto& e : aristas) {
        if (uf.unir(e.desde, e.hasta)) {
            total += e.costo;
            std::cout << e.desde << "-" << e.hasta << ": " << e.costo << '\n';
        }
    }
    return total;
}

int main() {
    const int costo = kruskal(5, {
        {0,1,3}, {0,2,1}, {1,2,2}, {1,3,4},
        {2,3,5}, {3,4,1}, {2,4,8}
    });
    std::cout << "Costo del MST: " << costo << '\n';
    if (costo != 8) throw std::logic_error("MST incorrecto");
}
