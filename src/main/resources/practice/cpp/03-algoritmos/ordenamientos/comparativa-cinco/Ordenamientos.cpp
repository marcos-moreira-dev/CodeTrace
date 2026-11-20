// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#include "Ordenamientos.hpp"
#include <algorithm>
#include <utility>

void Ordenamientos::insercion(std::vector<int>& a){
    for(std::size_t i=1;i<a.size();i++){
        int x=a[i];std::size_t j=i;
        while(j>0&&a[j-1]>x){a[j]=a[j-1];j--;}
        a[j]=x;
    }
}
void Ordenamientos::burbuja(std::vector<int>& a){
    std::size_t limite=a.size();
    while(limite>1){
        std::size_t ultimo=0;
        for(std::size_t j=1;j<limite;j++)if(a[j-1]>a[j]){
            std::swap(a[j-1],a[j]);ultimo=j;
        }
        limite=ultimo;
    }
}
void Ordenamientos::mezcla(std::vector<int>& a){
    std::vector<int> tmp(a.size());
    auto ordenar=[&](auto&& self,std::size_t lo,std::size_t hi)->void{
        if(hi-lo<=1)return;
        std::size_t m=lo+(hi-lo)/2;
        self(self,lo,m);self(self,m,hi);
        std::size_t i=lo,j=m;
        for(std::size_t k=lo;k<hi;k++){
            if(j==hi||(i<m&&a[i]<=a[j]))tmp[k]=a[i++];
            else tmp[k]=a[j++];
        }
        std::copy(tmp.begin()+static_cast<std::ptrdiff_t>(lo),
                  tmp.begin()+static_cast<std::ptrdiff_t>(hi),
                  a.begin()+static_cast<std::ptrdiff_t>(lo));
    };
    ordenar(ordenar,0,a.size());
}
void Ordenamientos::heap(std::vector<int>& a){
    auto hundir=[&](std::size_t n,std::size_t inicio){
        std::size_t i=inicio;
        while(true){
            std::size_t m=i,iz=2*i+1,de=iz+1;
            if(iz<n&&a[iz]>a[m])m=iz;
            if(de<n&&a[de]>a[m])m=de;
            if(m==i)return;
            std::swap(a[i],a[m]);i=m;
        }
    };
    for(std::size_t i=a.size()/2;i>0;){--i;hundir(a.size(),i);}
    for(std::size_t fin=a.size();fin>1;){
        --fin;std::swap(a[0],a[fin]);hundir(fin,0);
    }
}
void Ordenamientos::rapido(std::vector<int>& a){
    auto ordenar=[&](auto&& self,int lo,int hi)->void{
        if(lo>=hi)return;
        int pivote=a[hi],i=lo;
        for(int j=lo;j<hi;j++)if(a[j]<=pivote){
            std::swap(a[i],a[j]);i++;
        }
        std::swap(a[i],a[hi]);
        self(self,lo,i-1);self(self,i+1,hi);
    };
    ordenar(ordenar,0,static_cast<int>(a.size())-1);
}
