// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "ListaCircular.hpp"
#include <iostream>
#include <stdexcept>
#include <string>
#include <vector>

/**
 * Cliente lista circular: los enlaces no tienen nodo terminal null.
 * C++ requiere distinguir enlaces lógicos de propiedad de memoria:
 * vector<unique_ptr> libera nodos, Node* modela conexiones.
 * El límite de recorrido es el número de nodos del contenedor.
 */
int main(){
    ListaCircular<std::string> l;
    if(!l.valores().empty())throw std::logic_error("Vacía");
    for(auto x:{"Ana","Luis","Mia"})l.agregar(x);
    l.rotar();
    if(l.valores()!=std::vector<std::string>({"Luis","Mia","Ana"}))
        throw std::logic_error("Circular");
    std::cout<<"Lista circular OK\n";
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\linked-list\circular\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Lista circular OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
