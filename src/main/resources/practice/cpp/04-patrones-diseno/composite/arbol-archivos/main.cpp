// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Elementos.hpp"
#include <iostream>
#include <memory>
#include <stdexcept>

/**
 * Cliente Composite: carpeta y archivo comparten bytes(), sin
 * preguntas sobre tipo concreto. Un árbol pertenece a su raíz
 * mediante unique_ptr; destruirla libera todos los hijos.
 *
 * Esta estructura es acíclica por contrato. No modela todas las
 * validaciones de un grafo de enlaces o accesos al disco.
 */
int main(){
    auto raiz=std::make_unique<Carpeta>("proyecto");
    auto src=std::make_unique<Carpeta>("src");
    src->agregar(std::make_unique<Archivo>("Main.cpp",120));
    src->agregar(std::make_unique<Archivo>("App.cpp",80));
    raiz->agregar(std::move(src));
    raiz->agregar(std::make_unique<Archivo>("README.md",20));
    if(raiz->bytes()!=220)throw std::logic_error("Composite");
    std::cout<<"Bytes: "<<raiz->bytes()<<'\n';
}

/*
 * Salida esperada de consola:
 *   Bytes: 
 *   (los valores variables completan los fragmentos mostrados)
 * Verificacion: terminar sin excepciones inesperadas.
 */
