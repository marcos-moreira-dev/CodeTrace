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
