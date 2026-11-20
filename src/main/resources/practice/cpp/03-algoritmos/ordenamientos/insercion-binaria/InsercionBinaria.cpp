// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#include "InsercionBinaria.hpp"
#include <cstddef>

void insercionBinaria(std::vector<int>& a){
    for(std::size_t i=1;i<a.size();i++){
        int clave=a[i];
        std::size_t lo=0,hi=i;
        while(lo<hi){
            std::size_t m=lo+(hi-lo)/2;
            if(a[m]<=clave)lo=m+1;
            else hi=m;
        }
        for(std::size_t j=i;j>lo;j--)a[j]=a[j-1];
        a[lo]=clave;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\ordenamientos\insercion-binaria\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: InsercionBinaria.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
