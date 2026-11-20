// CodeTrace | Referencia: C++20, proyecto Observer
// Compilar: g++ -std=c++20 04-patrones-diseno/observer/main.cpp -o observer
#pragma once
#include "IObservador.hpp"
#include <string>
#include <vector>
class CentroEventos {
    std::vector<IObservador*> observadores_;
public:
    void registrar(IObservador& o) { observadores_.push_back(&o); }
    void publicar(const std::string& evento) {
        for (auto* o : observadores_) o->actualizar(evento);
    }
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\observer\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: CentroEventos.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
