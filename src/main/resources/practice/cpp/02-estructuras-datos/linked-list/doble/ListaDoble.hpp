// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <memory>
#include <stdexcept>
#include <vector>
#include <utility>

/**
 * Lista DOBLE: next es propietario único, prev es puntero observador.
 * El último nodo se libera en O(1) al desconectar el unique_ptr
 * del penúltimo; recorrer O(n), memoria O(n).
 */
template<class T>
class ListaDoble {
    struct Nodo {
        T dato;
        std::unique_ptr<Nodo> siguiente;
        Nodo* anterior=nullptr;
        explicit Nodo(T v):dato(std::move(v)){}
    };
    std::unique_ptr<Nodo> primero;
    Nodo* ultimo=nullptr;
public:
    void agregarFinal(T valor){
        auto n=std::make_unique<Nodo>(std::move(valor));
        Nodo* raw=n.get();
        raw->anterior=ultimo;
        if(ultimo)ultimo->siguiente=std::move(n);
        else primero=std::move(n);
        ultimo=raw;
    }
    T quitarFinal(){
        if(!ultimo)throw std::out_of_range("Lista vacía");
        T v=std::move(ultimo->dato);
        Nodo* anterior=ultimo->anterior;
        if(anterior)anterior->siguiente.reset();
        else primero.reset();
        ultimo=anterior;
        return v;
    }
    std::vector<T> adelante()const{
        std::vector<T> r;
        for(auto n=primero.get();n;n=n->siguiente.get())r.push_back(n->dato);
        return r;
    }
    std::vector<T> atras()const{
        std::vector<T> r;
        for(auto n=ultimo;n;n=n->anterior)r.push_back(n->dato);
        return r;
    }
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\linked-list\doble\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: ListaDoble.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Lista doble OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
