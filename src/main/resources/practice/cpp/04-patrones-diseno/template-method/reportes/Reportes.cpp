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
