// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Reportes.hpp"
#include <iostream>
#include <stdexcept>
#include <string>

/**
 * Cliente Template Method: la secuencia de validación y formato
 * permanece fija, pero la subclase implementa encabezado/detalle.
 * En C++ el método plantilla no es virtual; los puntos de
 * variación sí, y poseen un destructor virtual para polimorfismo.
 */
int main(){
    ReporteTexto texto;
    ReporteCsv csv;
    const ProcesoReporte& a=texto,&b=csv;
    if(a.ejecutar("ventas",15).find("Total: 15")==std::string::npos||
       b.ejecutar("ventas",15).find("ventas,15")==std::string::npos)
        throw std::logic_error("Reportes");
    std::cout<<a.ejecutar("ventas",15)<<b.ejecutar("ventas",15);
}
