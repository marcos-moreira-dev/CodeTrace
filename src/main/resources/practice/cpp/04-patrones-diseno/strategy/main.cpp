// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 main.cpp estrategias.cpp -o app.exe
// Ejecutar: ./app.exe

#include "estrategia.hpp"
#include <iostream>
/**
 * Cliente de Strategy y compilación separada con C++20.
 * Una estrategia define cómo calcular el total, otra puede sustituirla
 * sin modificar el cliente. La interfaz es un contrato, la fábrica
 * entrega un unique_ptr y el destructor virtual permite liberar
 * correctamente el objeto concreto.
 *
 * Desde la carpeta: g++ -std=c++20 main.cpp estrategias.cpp -o app.exe
 * Ejecutar: ./app.exe
 */
int main() {
    auto a = normal(), b = descuento();
    std::cout << a->total(100) << '\n' << b->total(100) << '\n';
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\strategy\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   100
 *   85
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
