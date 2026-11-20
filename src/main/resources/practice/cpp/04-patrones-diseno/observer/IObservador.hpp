// CodeTrace | Referencia: C++20, proyecto Observer
// Compilar: g++ -std=c++20 04-patrones-diseno/observer/main.cpp -o observer
#pragma once
#include <string>
class IObservador {
public:
    virtual ~IObservador() = default;
    virtual void actualizar(const std::string& evento) = 0;
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\observer\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: IObservador.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
