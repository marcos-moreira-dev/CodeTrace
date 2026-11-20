// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\04-patrones-diseno\decorator_notificaciones.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <iostream>
#include <memory>
#include <string>
#include <utility>

/**
 * Cliente Decorator con RAII y composición.
 *
 * Un contrato de notificaciones puede envolverse por capas sin
 * derivar todas las combinaciones posibles. Cada decorador posee
 * su componente interno mediante unique_ptr, y la liberación
 * recursiva se gestiona automáticamente.
 */
struct Notificador {
    virtual ~Notificador() = default;
    virtual void enviar(const std::string& mensaje) const = 0;
};
struct Consola final : Notificador {
    void enviar(const std::string& m) const override { std::cout << m << '\n'; }
};
class Prefijo final : public Notificador {
    std::unique_ptr<Notificador> interno;
    std::string etiqueta;
public:
    Prefijo(std::unique_ptr<Notificador> n, std::string texto)
        : interno(std::move(n)), etiqueta(std::move(texto)) {}
    void enviar(const std::string& m) const override { interno->enviar(etiqueta + m); }
};
int main() {
    std::unique_ptr<Notificador> n =
        std::make_unique<Prefijo>(std::make_unique<Consola>(), "[INFO] ");
    n->enviar("Conexión disponible");
}
