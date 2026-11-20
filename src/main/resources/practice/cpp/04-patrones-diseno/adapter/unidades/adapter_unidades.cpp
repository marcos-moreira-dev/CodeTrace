// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\04-patrones-diseno\adapter\unidades\adapter_unidades.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <iostream>

/**
 * Cliente Adapter: separar el contrato propio de una API externa.
 *
 * Un proveedor de termómetros expone Fahrenheit, mientras el dominio
 * necesita Celsius. El adaptador implementa el contrato deseado
 * y envuelve al proveedor, sin modificar su implementación.
 */
struct SensorCelsius {
    virtual ~SensorCelsius() = default;
    virtual double leer() const = 0;
};
class Legado {
public:
    double fahrenheit() const { return 77.0; }
};
class Adaptador final : public SensorCelsius {
    const Legado& externo;
public:
    explicit Adaptador(const Legado& proveedor) : externo(proveedor) {}
    double leer() const override { return (externo.fahrenheit() - 32) * 5 / 9; }
};
int main() {
    Legado proveedor;
    Adaptador sensor(proveedor);
    const SensorCelsius& contrato = sensor;
    std::cout << "Celsius: " << contrato.leer() << '\n';
}
