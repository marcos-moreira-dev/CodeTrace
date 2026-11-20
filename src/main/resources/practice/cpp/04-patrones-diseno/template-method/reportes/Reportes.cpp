// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Reportes.hpp"
#include <stdexcept>
#include <string>

std::string ProcesoReporte::ejecutar(const std::string& t,int total)const{
    if(t.empty()||total<0)throw std::invalid_argument("Reporte");
    return encabezado(t)+detalle(total)+pie();
}
std::string ProcesoReporte::pie()const{return "\n";}
std::string ReporteTexto::encabezado(const std::string& t)const{return "== "+t+" ==\n";}
std::string ReporteTexto::detalle(int x)const{return "Total: "+std::to_string(x);}
std::string ReporteCsv::encabezado(const std::string&)const{return "titulo,total\n";}
std::string ReporteCsv::detalle(int x)const{return "ventas,"+std::to_string(x);}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\template-method\reportes\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Reportes.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
