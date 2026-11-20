// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "DequeCurso.hpp"
#include <iostream>
#include <stdexcept>
#include <string>

/**
 * Cliente Deque: una estructura puede comportarse como pila y cola.
 * C++ proporciona std::deque sin forzar una lista doble manual;
 * esta se explica en linked-list/doble. Es idiomático aprovechar STL
 * cuando lo que se estudia es el contrato y no la implementación.
 */
int main(){
    DequeCurso<std::string> d;
    d.insertarPrimero("B");d.insertarPrimero("A");d.insertarUltimo("C");
    if(d.quitarPrimero()!="A"||d.quitarUltimo()!="C"||
       d.quitarUltimo()!="B")throw std::logic_error("Deque");
    std::cout<<"Deque correcta\n";
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\deque\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Deque correcta
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
