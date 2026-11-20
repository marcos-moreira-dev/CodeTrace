// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Solicitud.hpp"
#include <stdexcept>
#include <utility>

SolicitudBuilder& SolicitudBuilder::ruta(std::string v){
    ruta_=std::move(v);return *this;
}
SolicitudBuilder& SolicitudBuilder::metodo(std::string v){
    metodo_=std::move(v);return *this;
}
SolicitudBuilder& SolicitudBuilder::cabecera(std::string v){
    cabeceras_.push_back(std::move(v));return *this;
}
Solicitud SolicitudBuilder::construir()const{
    if(ruta_.empty()||ruta_.front()!='/'||metodo_.empty())
        throw std::logic_error("Solicitud inválida");
    return {ruta_,metodo_,cabeceras_};
}
