// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <queue>
#include <vector>
#include <stdexcept>

/**
 * Max heap idiomático C++20: std::priority_queue<int> usa
 * un heap binario por defecto, con el máximo en top().
 * push/pop O(log n), top O(1). Para estudiar sift-down manual
 * comparar con Java/TypeScript/Python.
 */
class HeapMaximo{
    std::priority_queue<int> heap;
public:
    void insertar(int valor){heap.push(valor);}
    int extraer(){
        if(heap.empty())throw std::out_of_range("Heap vacío");
        int v=heap.top();heap.pop();return v;
    }
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\heap-maximo\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: HeapMaximo.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Max heap correcto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
