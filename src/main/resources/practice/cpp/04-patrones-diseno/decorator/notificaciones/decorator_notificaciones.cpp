// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "Notificadores.hpp"
#include <iostream>
#include <stdexcept>
#include <utility>

void Consola::enviar(const std::string& m) const { std::cout << m << '\n'; }

Prefijo::Prefijo(std::unique_ptr<Notificador> n, std::string texto)
    : interno(std::move(n)), etiqueta(std::move(texto)) {
    if (!interno) throw std::invalid_argument("Notificador interno nulo");
}
void Prefijo::enviar(const std::string& m) const {
    interno->enviar(etiqueta + m);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\decorator\notificaciones\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: decorator_notificaciones.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   [INFO] Conexión disponible
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
