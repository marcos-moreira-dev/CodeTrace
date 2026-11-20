// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "ListaDoble.hpp"
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente Linked List doble: referencia propietaria hacia siguiente,
 * referencia no propietaria hacia anterior. La diferencia importa:
 * usar dos shared_ptr fuertes crearía ciclos y fugas potenciales.
 *
 * La extracción del extremo cuesta O(1), sin recorridos intermedios.
 */
int main(){
    ListaDoble<int> l;
    for(int x:{2,3,5})l.agregarFinal(x);
    if(l.adelante()!=std::vector<int>({2,3,5}) ||
       l.atras()!=std::vector<int>({5,3,2}) ||
       l.quitarFinal()!=5)throw std::logic_error("Doble");
    std::cout<<"Lista doble OK\n";
}

/*
 * Salida esperada de consola:
 *   Lista doble OK\n
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: las aserciones se cumplen y el proceso termina sin error.
 */
