// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <memory>
#include <vector>
#include <utility>

/**
 * Lista doble circular: un almacenamiento RAII independiente de
 * enlaces bidireccionales de observación. first.prev=last;
 * last.next=first. Recorrer O(n), insertar O(1) amortizado.
 */
template<class T>
class ListaDobleCircular {
    struct Nodo{
        T dato;
        Nodo* anterior=nullptr;
        Nodo* siguiente=nullptr;
        explicit Nodo(T valor):dato(std::move(valor)){}
    };
    std::vector<std::unique_ptr<Nodo>> almacenamiento;
    Nodo* ultimo=nullptr;
public:
    void agregar(T valor){
        auto n=std::make_unique<Nodo>(std::move(valor));
        auto raw=n.get();
        if(!ultimo){raw->anterior=raw;raw->siguiente=raw;}
        else{
            auto primero=ultimo->siguiente;
            raw->anterior=ultimo;raw->siguiente=primero;
            ultimo->siguiente=raw;primero->anterior=raw;
        }
        ultimo=raw;
        almacenamiento.push_back(std::move(n));
    }
    std::vector<T> valores(bool inverso=false)const{
        std::vector<T> r;
        if(!ultimo)return r;
        auto n=inverso?ultimo:ultimo->siguiente;
        for(std::size_t i=0;i<almacenamiento.size();i++){
            r.push_back(n->dato);
            n=inverso?n->anterior:n->siguiente;
        }
        return r;
    }
};
