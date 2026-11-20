// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "GrafoPonderado.hpp"
#include <iostream>
#include <stdexcept>

/**
 * Cliente grafo ponderado: A->B y B->A no son lo mismo.
 * Las latencias forman pesos y la representación es independiente
 * de Dijkstra/Bellman-Ford. std::unordered_map ofrece un contrato
 * hash, distinto del arreglo de índice entero usado en Java.
 */
int main(){
    GrafoPonderado g;
    g.conectar("GYE","UIO",12);
    g.conectar("GYE","CUE",18);
    g.conectar("UIO","CUE",8);
    if(g.vecinos("GYE").size()!=2||!g.vecinos("CUE").empty())
        throw std::logic_error("Arcos");
    std::cout<<"Arcos desde GYE: "<<g.vecinos("GYE").size()<<'\n';
}

/*
 * Salida esperada de consola:
 *   Arcos desde GYE: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: las aserciones se cumplen y el proceso termina sin error.
 */
