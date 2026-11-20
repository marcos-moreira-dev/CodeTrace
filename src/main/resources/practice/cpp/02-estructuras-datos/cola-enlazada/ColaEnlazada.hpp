// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <memory>
#include <stdexcept>
#include <utility>

/** Queue FIFO con unique_ptr next y tail no propietario. O(1) ambas operaciones. */
template<class T>
class ColaEnlazada {
    struct Nodo{
        T valor;
        std::unique_ptr<Nodo> siguiente;
        explicit Nodo(T v):valor(std::move(v)){}
    };
    std::unique_ptr<Nodo> primero;
    Nodo* ultimo=nullptr;
public:
    void encolar(T v){
        auto n=std::make_unique<Nodo>(std::move(v));
        Nodo* raw=n.get();
        if(ultimo)ultimo->siguiente=std::move(n);
        else primero=std::move(n);
        ultimo=raw;
    }
    T desencolar(){
        if(!primero)throw std::out_of_range("Cola vacía");
        T v=std::move(primero->valor);
        primero=std::move(primero->siguiente);
        if(!primero)ultimo=nullptr;
        return v;
    }
    bool vacia()const{return !primero;}
};
