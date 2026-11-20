// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#include "Ordenamientos.hpp"
#include <array>
#include <iostream>
#include <stdexcept>
#include <string_view>
#include <utility>
#include <vector>

/**
 * Cliente de comparativa de cinco sorts: mismo input para todos.
 *
 * El arreglo original se copia antes de aplicar cada algoritmo.
 * Insertion y Bubble son adaptativos; Quick con pivote final tiene
 * riesgo O(n^2). Una prueba funcional no mide complejidad.
 * C++ expresa funcion por puntero, sin crear clases de estrategia.
 */
int main(){
    using Ordenador=void (*)(std::vector<int>&);
    const std::array<std::pair<std::string_view,Ordenador>,5> metodos{{
        {"Insercion",Ordenamientos::insercion},
        {"Burbuja",Ordenamientos::burbuja},
        {"Mezcla",Ordenamientos::mezcla},
        {"HeapSort",Ordenamientos::heap},
        {"QuickSort",Ordenamientos::rapido}
    }};
    const std::vector<int> esperado{1,2,3,5,7,8,9};
    for(const auto& [nombre,fn]:metodos){
        std::vector<int> datos{9,3,8,1,7,2,5};
        fn(datos);
        if(datos!=esperado)throw std::logic_error("Sort");
        std::cout<<nombre<<": [";
        for(std::size_t i=0;i<datos.size();i++){
            if(i)std::cout<<", ";
            std::cout<<datos[i];
        }
        std::cout<<"]\n";
        std::vector<int> vacio,repetidos{3,1,3,1};
        fn(vacio);fn(repetidos);
        if(repetidos!=std::vector<int>({1,1,3,3}))
            throw std::logic_error("Casos limite");
    }
}
/*
 * Salida esperada:
 * Insercion: [1, 2, 3, 5, 7, 8, 9]
 * Burbuja: [1, 2, 3, 5, 7, 8, 9]
 * Mezcla: [1, 2, 3, 5, 7, 8, 9]
 * HeapSort: [1, 2, 3, 5, 7, 8, 9]
 * QuickSort: [1, 2, 3, 5, 7, 8, 9]
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\ordenamientos\comparativa-cinco\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
