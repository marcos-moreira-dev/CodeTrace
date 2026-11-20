// CodeTrace | Referencia: C++20, proyecto Observer
// Compilar: g++ -std=c++20 04-patrones-diseno/observer/main.cpp -o observer
#include "CentroEventos.hpp"
#include <iostream>
struct Consola final : IObservador {
    void actualizar(const std::string& e) override { std::cout << e << '\n'; }
};
int main() {
    CentroEventos centro;
    Consola pantalla;
    centro.registrar(pantalla);
    centro.publicar("Factura 001");
}

/*
 * Salida esperada de consola:
 *   Se muestran los datos o el resultado calculado del ejemplo.
 * Verificacion: terminar sin excepciones inesperadas.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\observer\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
