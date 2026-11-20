// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "Pila.hpp"
#include <iostream>
#include <stdexcept>
#include <string>

/**
 * Cliente LIFO con template y move semantics.
 *
 * La implementación de pila es genérica; no asume números.
 * Mover valores evita copias cuando T es costoso o no copiable.
 * La capacidad de vector crece amortizadamente, no como una
 * lista enlazada. std::stack es la alternativa estándar.
 */
int main() {
    Pila<std::string> pila;
    pila.apilar("A"); pila.apilar("B"); pila.apilar("C");
    if (pila.desapilar() != "C" || pila.desapilar() != "B"
        || pila.cantidad() != 1) throw std::logic_error("LIFO");
    std::cout << "Pila LIFO correcta\n";
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\pila\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Pila LIFO correcta
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
