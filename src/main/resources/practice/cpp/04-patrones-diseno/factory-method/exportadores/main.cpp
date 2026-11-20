// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "Exportadores.hpp"
#include <iostream>
#include <memory>
#include <stdexcept>

/**
 * Cliente Factory Method en C++20 con interfaz, creador y producto.
 *
 * CreadorTexto decide qué Exportador devuelve a través del método
 * protegido crear(). El algoritmo preparar() no necesita conocerlo.
 * Las dependencias se transmiten por unique_ptr, con destrucción
 * determinista y sin fugas. Contrastar con una función fábrica
 * simple que no usa herencia entre creadores.
 */
int main() {
    std::unique_ptr<Creador> creador = std::make_unique<CreadorTexto>();
    const auto salida = creador->preparar("Factura 001");
    std::cout << salida << '\n';
    if (salida != "TXT: Factura 001") throw std::logic_error("Fábrica");
}
