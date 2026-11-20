// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <vector>
#include <stdexcept>
#include <numeric>
#include <utility>

/**
 * DSU: union by size + path compression, O(alfa(n)) amortizado.
 * El representante identifica una componente, no una ruta.
 */
class UnionFind{
    std::vector<int> padre,tamanio;
    int componentes_;
public:
    explicit UnionFind(int n):padre(n),tamanio(n,1),componentes_(n){
        if(n<=0)throw std::invalid_argument("n");
        std::iota(padre.begin(),padre.end(),0);
    }
    int raiz(int x){
        if(padre.at(x)!=x)padre[x]=raiz(padre[x]);
        return padre[x];
    }
    bool unir(int a,int b){
        int x=raiz(a),y=raiz(b);
        if(x==y)return false;
        if(tamanio[x]<tamanio[y])std::swap(x,y);
        padre[y]=x;tamanio[x]+=tamanio[y];componentes_--;
        return true;
    }
    bool conectados(int a,int b){return raiz(a)==raiz(b);}
    int componentes()const{return componentes_;}
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\union-find\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: UnionFind.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Componentes: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
