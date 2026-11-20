// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "Sensores.hpp"

/** Transformación de la API externa a la unidad requerida. */
double Legado::fahrenheit() const { return 77.0; }
Adaptador::Adaptador(const Legado& proveedor) : externo(proveedor) {}
double Adaptador::leer() const { return (externo.fahrenheit() - 32.0) * 5.0 / 9.0; }
