// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#pragma once
#include <string>
#include <vector>

/**
 * Producto con constructor por valores y Builder fluido.
 * El producto es independiente del estado mutable del Builder,
 * pues std::vector y std::string conservan sus propios valores.
 */
struct Solicitud {
    std::string ruta;
    std::string metodo;
    std::vector<std::string> cabeceras;
};

class SolicitudBuilder{
    std::string ruta_,metodo_="GET";
    std::vector<std::string> cabeceras_;
public:
    SolicitudBuilder& ruta(std::string valor);
    SolicitudBuilder& metodo(std::string valor);
    SolicitudBuilder& cabecera(std::string texto);
    Solicitud construir()const;
};
