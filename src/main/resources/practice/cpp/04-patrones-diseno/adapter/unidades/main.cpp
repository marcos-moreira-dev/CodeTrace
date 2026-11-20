// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "Sensores.hpp"
#include <iostream>
#include <stdexcept>

/**
 * Cliente Adapter: contratos incompatibles entre Fahrenheit y Celsius.
 *
 * 1. SensorCelsius es el contrato que el negocio conoce.
 * 2. Legado es una API externa que no se modifica.
 * 3. Adaptador contiene una referencia a Legado y traduce la llamada.
 * 4. Este cliente construye objetos y conserva sus vidas útiles.
 *
 * No confundir Adapter con Decorator: el primero traduce contratos,
 * el segundo conserva el contrato y amplía comportamiento.
 */
int main() {
    Legado proveedor;
    Adaptador adaptado(proveedor);
    const SensorCelsius& contrato = adaptado;
    std::cout << "Celsius: " << contrato.leer() << '\n';
    if (contrato.leer() != 25.0) throw std::logic_error("Conversión");
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\adapter\unidades\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Celsius: 25
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
