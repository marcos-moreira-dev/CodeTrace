// CodeTrace | Referencia: C++20, Dijkstra con cola de prioridad
// Compilar: g++ -std=c++20 Dijkstra.cpp -o dijkstra
#include <functional>
#include <iostream>
#include <limits>
#include <queue>
#include <stdexcept>
#include <utility>
#include <vector>

using Arista = std::pair<int, int>; // destino y peso
std::vector<int> dijkstra(const std::vector<std::vector<Arista>>& grafo, int inicio) {
    const int infinito = std::numeric_limits<int>::max();
    std::vector<int> distancia(grafo.size(), infinito);
    using Estado = std::pair<int, int>; // distancia y nodo
    std::priority_queue<Estado, std::vector<Estado>, std::greater<>> cola;
    distancia[inicio] = 0;
    cola.push({0, inicio});
    while (!cola.empty()) {
        auto [costo, nodo] = cola.top();
        cola.pop();
        if (costo != distancia[nodo]) continue;
        for (auto [destino, peso] : grafo[nodo]) {
            if (peso < 0) throw std::invalid_argument("Peso negativo");
            if (costo <= infinito - peso && costo + peso < distancia[destino]) {
                distancia[destino] = costo + peso;
                cola.push({distancia[destino], destino});
            }
        }
    }
    return distancia;
}
int main() {
    std::vector<std::vector<Arista>> grafo = {{{1, 2}, {2, 8}}, {{2, 3}}, {}};
    for (int valor : dijkstra(grafo, 0)) std::cout << valor << ' ';
    std::cout << '\n';
}
