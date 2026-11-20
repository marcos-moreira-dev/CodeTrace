// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#pragma once
#include <string>

/**
 * Template Method: un método NO virtual fija el orden de los pasos,
 * mientras los pasos protegidos sí son virtuales. Evita mezclar
 * herencia del algoritmo con el comportamiento recibido de Strategy.
 */
class ProcesoReporte{
public:
    virtual ~ProcesoReporte()=default;
    std::string ejecutar(const std::string& titulo,int total)const;
protected:
    virtual std::string encabezado(const std::string& titulo)const=0;
    virtual std::string detalle(int total)const=0;
    virtual std::string pie()const;
};
class ReporteTexto final:public ProcesoReporte{
protected:
    std::string encabezado(const std::string& titulo)const override;
    std::string detalle(int total)const override;
};
class ReporteCsv final:public ProcesoReporte{
protected:
    std::string encabezado(const std::string& titulo)const override;
    std::string detalle(int total)const override;
};
