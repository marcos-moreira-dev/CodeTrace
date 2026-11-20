// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#pragma once
#include <stdexcept>
#include <utility>
#include <vector>

/**
 * Pila LIFO genérica con vector<T> como almacenamiento.
 * push_back/pop_back cuestan O(1) amortizado; si se amplia
 * la capacidad puede haber movimiento de elementos O(n).
 */
template<class T>
class Pila {
    std::vector<T> datos;
public:
    void apilar(T valor) { datos.push_back(std::move(valor)); }
    T desapilar() {
        if (datos.empty()) throw std::out_of_range("Pila vacía");
        T valor = std::move(datos.back());
        datos.pop_back();
        return valor;
    }
    size_t cantidad() const { return datos.size(); }
};
