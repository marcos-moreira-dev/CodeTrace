// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#pragma once
#include <memory>
#include <string>

/**
 * Abstract Factory: contrato para familias de Boton y Dialogo.
 * unique_ptr expresa que el cliente recibe propiedad de cada
 * producto sin encargarse de delete o conteo manual de referencias.
 */
struct Boton {
    virtual ~Boton()=default;
    virtual std::string pintar()const=0;
};
struct Dialogo{
    virtual ~Dialogo()=default;
    virtual std::string pintar()const=0;
};
struct Fabrica{
    virtual ~Fabrica()=default;
    virtual std::unique_ptr<Boton> boton()const=0;
    virtual std::unique_ptr<Dialogo> dialogo()const=0;
};
std::unique_ptr<Fabrica> fabricaClara();
std::unique_ptr<Fabrica> fabricaOscura();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\abstract-factory\tema-consola\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Familias.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
