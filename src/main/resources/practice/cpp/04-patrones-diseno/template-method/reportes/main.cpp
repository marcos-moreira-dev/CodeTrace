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

/*
 * Salida esperada de consola (cuatro líneas):
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * El método plantilla fija validación y pasos; las subclases aportan el formato.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\template-method\reportes\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
