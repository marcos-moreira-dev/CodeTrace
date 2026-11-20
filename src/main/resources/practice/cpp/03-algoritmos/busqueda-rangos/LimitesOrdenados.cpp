// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#include "LimitesOrdenados.hpp"
#include <cstddef>

std::size_t lowerBound(const std::vector<int>& a,int x){
    std::size_t lo=0,hi=a.size();
    while(lo<hi){
        std::size_t m=lo+(hi-lo)/2;
        if(a[m]<x)lo=m+1;else hi=m;
    }
    return lo;
}
std::size_t upperBound(const std::vector<int>& a,int x){
    std::size_t lo=0,hi=a.size();
    while(lo<hi){
        std::size_t m=lo+(hi-lo)/2;
        if(a[m]<=x)lo=m+1;else hi=m;
    }
    return lo;
}
