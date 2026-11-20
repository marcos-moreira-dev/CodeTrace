// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Solicitud.hpp"
#include <iostream>
#include <stdexcept>

/**
 * Cliente Builder en C++20: construir por etapas con API encadenable.
 *
 * A diferencia de Java y el GC, las strings/vectores son valores que
 * poseen su propia memoria. std::move en los setters evita copias
 * innecesarias; construir produce un producto independiente.
 */
int main(){
    SolicitudBuilder b;
    auto s=b.ruta("/facturas").metodo("POST")
        .cabecera("Content-Type: application/json").construir();
    if(s.ruta!="/facturas"||s.cabeceras.size()!=1)throw std::logic_error("Builder");
    std::cout<<s.metodo<<" "<<s.ruta<<" ("<<s.cabeceras.size()<<" cabecera)"<<'\n';
}

/*
 * Salida esperada de consola:
 *   POST /facturas (1 cabecera)
 * La validación del Builder entrega un producto con una cabecera.
 */
