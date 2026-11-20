// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "Trie.hpp"
#include <iostream>
#include <stdexcept>

/**
 * Cliente Trie: un prefijo presente no equivale a una palabra.
 *
 * Usamos string_view para no copiar texto al buscar y unique_ptr
 * para expresar propiedad de subárboles; los caracteres se tratan
 * como bytes de codificación, no como grafemas Unicode completos.
 */
int main(){
    Trie t;
    t.insertar("casa");t.insertar("casco");
    if(!t.prefijo("cas")||t.contiene("cas")||
       !t.contiene("casa")||t.contiene("cama"))throw std::logic_error("Trie");
    std::cout<<"Trie de prefijos OK\n";
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\trie\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Trie de prefijos OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
