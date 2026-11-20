// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\03-algoritmos\bfs-dfs\bfs_dfs.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <deque>
#include <iostream>
#include <map>
#include <set>
#include <stack>
#include <string>
#include <vector>

/**
 * Cliente de recorridos BFS y DFS sobre listas de adyacencia.
 *
 * BFS usa una cola para recorrer niveles desde el origen.
 * DFS usa una pila y profundiza antes de retroceder. Ambas
 * técnicas visitan O(V+E) relaciones bajo una lista de
 * adyacencias y guardan hasta O(V) vértices auxiliares.
 */
using Grafo = std::map<std::string, std::vector<std::string>>;
void bfs(const Grafo& g, const std::string& inicio) {
    std::deque<std::string> cola{inicio};
    std::set<std::string> visto{inicio};
    while (!cola.empty()) {
        auto v = cola.front(); cola.pop_front();
        std::cout << v << ' ';
        for (const auto& w : g.at(v)) if (visto.insert(w).second) cola.push_back(w);
    }
}
void dfs(const Grafo& g, const std::string& inicio) {
    std::stack<std::string> pila;
    std::set<std::string> visto;
    pila.push(inicio);
    while (!pila.empty()) {
        auto v = pila.top(); pila.pop();
        if (!visto.insert(v).second) continue;
        std::cout << v << ' ';
        const auto& vecinos = g.at(v);
        for (auto it = vecinos.rbegin(); it != vecinos.rend(); ++it)
            if (!visto.contains(*it)) pila.push(*it);
    }
}
int main() {
    Grafo g{{"A", {"B", "C"}}, {"B", {"D"}}, {"C", {"E"}}, {"D", {}}, {"E", {}}};
    std::cout << "BFS: "; bfs(g, "A");
    std::cout << "\nDFS: "; dfs(g, "A");
    std::cout << '\n';
}
