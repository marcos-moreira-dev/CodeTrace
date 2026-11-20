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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\builder\solicitud\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Solicitud.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   POST /facturas (1 cabecera)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
