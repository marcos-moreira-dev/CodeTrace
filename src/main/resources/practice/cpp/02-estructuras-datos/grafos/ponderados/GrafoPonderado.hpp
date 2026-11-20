// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <cmath>
#include <string>
#include <stdexcept>
#include <unordered_map>
#include <vector>

/**
 * Grafo dirigido y ponderado con adyacencias; memoria O(V+E).
 * La estructura permite pesos negativos pero Dijkstra NO; elegir
 * algoritmo corresponde al cliente de algoritmos, no al modelo.
 */
struct Arista{
    std::string destino;
    double peso;
};
class GrafoPonderado{
    std::unordered_map<std::string,std::vector<Arista>> ady;
public:
    void conectar(std::string origen,std::string destino,double peso){
        if(!std::isfinite(peso))throw std::invalid_argument("Peso");
        ady[origen].push_back({destino,peso});
        ady.try_emplace(destino);
    }
    const std::vector<Arista>& vecinos(const std::string& origen)const{
        return ady.at(origen);
    }
};
