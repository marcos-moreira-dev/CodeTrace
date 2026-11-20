// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\01-fundamentos\optional-variant\optional_variant.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <iostream>
#include <optional>
#include <string>
#include <variant>

/**
 * Cliente de std::optional y std::variant.
 *
 * optional<T> expresa un valor que puede no existir, sin un centinela.
 * variant<A,B> representa una alternativa de tipos conocidos y obliga
 * a considerar cuál está activa. Son tipos valor; no requieren
 * asignar dinámicamente un objeto polimórfico.
 */
std::optional<int> leer_id(std::string texto) {
    if (texto == "42") return 42;
    return std::nullopt;
}
int main() {
    std::variant<int, std::string> resultado = std::string("pendiente");
    std::visit([](const auto& x) { std::cout << x << '\n'; }, resultado);
    if (auto id = leer_id("42")) std::cout << "ID: " << *id << '\n';
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\01-fundamentos\optional-variant\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   pendiente
 *   ID: 42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
