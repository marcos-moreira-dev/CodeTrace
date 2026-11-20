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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\template-method\reportes\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Reportes.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
