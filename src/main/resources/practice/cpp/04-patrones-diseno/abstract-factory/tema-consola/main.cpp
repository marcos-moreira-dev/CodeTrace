// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Familias.hpp"
#include <iostream>
#include <stdexcept>
#include <string>

/**
 * Cliente Abstract Factory: obtener DOS productos de una familia.
 *
 * No seleccionamos clases concretas de botón y diálogo desde este
 * archivo, sino una fábrica coherente. El cliente adquiere y suelta
 * los productos mediante RAII; no se necesitan vistas reales.
 */
std::string pantalla(const Fabrica& f){
    return f.boton()->pintar()+" / "+f.dialogo()->pintar();
}
int main(){
    auto claro=fabricaClara(),oscuro=fabricaOscura();
    auto a=pantalla(*claro),b=pantalla(*oscuro);
    if(a==b||a.find("clara")==std::string::npos ||
        b.find("oscura")==std::string::npos)throw std::logic_error("Familias");
    std::cout<<a<<'\n'<<b<<'\n';
}

/*
 * Salida esperada de consola:
 *   Se muestran los datos o el resultado calculado del ejemplo.
 * Verificacion: terminar sin excepciones inesperadas.
 */
