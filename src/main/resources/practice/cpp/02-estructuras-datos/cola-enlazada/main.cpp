// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "ColaEnlazada.hpp"
#include <iostream>
#include <stdexcept>
#include <string>

/**
 * Cliente cola enlazada: observar la propiedad RAII al extraer la
 * cabeza y reestablecer tail=nullptr cuando se queda vacía.
 * Comparar con std::queue y un buffer circular de capacidad fija.
 */
int main(){
    ColaEnlazada<std::string> q;
    q.encolar("A");q.encolar("B");
    if(q.desencolar()!="A"||q.desencolar()!="B")throw std::logic_error("FIFO");
    q.encolar("C");
    if(q.desencolar()!="C"||!q.vacia())throw std::logic_error("Tail");
    std::cout<<"Cola enlazada OK\n";
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\cola-enlazada\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Cola enlazada OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
