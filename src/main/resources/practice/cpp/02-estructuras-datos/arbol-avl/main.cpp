// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "ArbolAVL.hpp"
#include <iostream>
#include <numeric>
#include <stdexcept>
#include <vector>

/**
 * Cliente AVL: inserción ya ordenada, adversa para un BST simple.
 *
 * Las rotaciones cambian la PROPIEDAD de los subárboles mediante
 * move semantics; unique_ptr evita fugas, pero obliga a razonar
 * sobre transferencias. Validamos inorden y altura de cada nodo.
 */
int main(){
    ArbolAVL arbol;
    for(int i=1;i<=15;i++)arbol.insertar(i);
    std::vector<int> esperado(15);
    std::iota(esperado.begin(),esperado.end(),1);
    if(arbol.inorden()!=esperado||arbol.altura()>5)
        throw std::logic_error("AVL");
    std::cout<<"Altura AVL: "<<arbol.altura()<<'\n';
}

/*
 * Salida esperada de consola (exacta para los datos del cliente):
 * Altura AVL: 4
 * Verificacion: exit code 0 y ninguna excepcion inesperada.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\arbol-avl\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Altura AVL: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
