// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <memory>
#include <vector>
#include <utility>

/**
 * Lista CIRCULAR: vector de unique_ptr posee los nodos;
 * punteros crudos son referencias NO propietarias del anillo.
 * Así evitamos ciclos de ownership con shared_ptr/unique_ptr.
 * Insertar O(1) amortizado (vector), rotar O(1), recorrer O(n).
 */
template<class T>
class ListaCircular {
    struct Nodo {
        T dato;
        Nodo* siguiente=nullptr;
        explicit Nodo(T x):dato(std::move(x)){}
    };
    std::vector<std::unique_ptr<Nodo>> almacenamiento;
    Nodo* ultimo=nullptr;
public:
    void agregar(T valor){
        auto n=std::make_unique<Nodo>(std::move(valor));
        Nodo* raw=n.get();
        raw->siguiente=ultimo?ultimo->siguiente:raw;
        if(ultimo)ultimo->siguiente=raw;
        ultimo=raw;
        almacenamiento.push_back(std::move(n));
    }
    void rotar(){if(ultimo)ultimo=ultimo->siguiente;}
    std::vector<T> valores()const{
        std::vector<T> r;
        if(!ultimo)return r;
        auto n=ultimo->siguiente;
        for(std::size_t i=0;i<almacenamiento.size();i++,n=n->siguiente)r.push_back(n->dato);
        return r;
    }
};
