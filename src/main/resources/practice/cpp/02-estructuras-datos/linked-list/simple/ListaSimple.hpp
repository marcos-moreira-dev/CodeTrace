// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <memory>
#include <vector>
#include <utility>

/**
 * Linked List simple con propiedad exclusiva de nodos vía unique_ptr.
 * Cada Nodo posee siguiente y la lista posee cabeza. Insertar en
 * cabeza O(1); recorrer O(n), espacio O(n), sin new/delete manual.
 */
template<class T>
class ListaSimple {
    struct Nodo {
        T valor;
        std::unique_ptr<Nodo> siguiente;
        Nodo(T v, std::unique_ptr<Nodo> s)
            : valor(std::move(v)), siguiente(std::move(s)) {}
    };
    std::unique_ptr<Nodo> primero;
public:
    void agregarPrimero(T v) {
        primero=std::make_unique<Nodo>(std::move(v),std::move(primero));
    }
    std::vector<T> valores() const {
        std::vector<T> r;
        for(const Nodo* n=primero.get();n;n=n->siguiente.get())r.push_back(n->valor);
        return r;
    }
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\linked-list\simple\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: ListaSimple.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Linked list simple OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
