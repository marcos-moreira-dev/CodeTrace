// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "UnionFind.hpp"
#include <iostream>
#include <stdexcept>

/**
 * Cliente Union-Find: conectividad incremental para Kruskal.
 *
 * Compresión de caminos y unión por tamaño dan costo amortizado
 * cercano a constante, no reconstruyen un camino. Se verifica
 * que unir dos veces la misma componente no cambia el contador.
 */
int main(){
    UnionFind d(5);
    if(!d.unir(0,1)||!d.unir(1,2)||d.unir(0,2)||
       !d.conectados(0,2)||d.conectados(0,4)||d.componentes()!=3)
        throw std::logic_error("DSU");
    std::cout<<"Componentes: "<<d.componentes()<<'\n';
}
