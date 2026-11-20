// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <deque>
#include <stdexcept>
#include <utility>

/**
 * std::deque es un contenedor nativo de C++ con doble extremo.
 * push_front/back y pop_front/back O(1) amortizado. A diferencia
 * de std::vector, no asegura un almacenamiento plano contiguo.
 */
template<class T>
class DequeCurso {
    std::deque<T> datos;
public:
    void insertarPrimero(T v){datos.push_front(std::move(v));}
    void insertarUltimo(T v){datos.push_back(std::move(v));}
    T quitarPrimero(){
        if(datos.empty())throw std::out_of_range("Deque vacío");
        T v=std::move(datos.front());datos.pop_front();return v;
    }
    T quitarUltimo(){
        if(datos.empty())throw std::out_of_range("Deque vacío");
        T v=std::move(datos.back());datos.pop_back();return v;
    }
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\deque\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: DequeCurso.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Deque correcta
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
