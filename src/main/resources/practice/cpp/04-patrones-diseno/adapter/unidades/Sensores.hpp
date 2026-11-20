// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#pragma once

/**
 * Contrato del dominio y proveedor ajeno a la aplicación.
 *
 * La clase Adaptador almacena una REFERENCIA no propietaria a Legado:
 * su vida útil debe superar a la del adaptador. C++ no usa GC para
 * prolongar automáticamente la vida del referente.
 */
struct SensorCelsius {
    virtual ~SensorCelsius() = default;
    virtual double leer() const = 0;
};

class Legado {
public:
    double fahrenheit() const;
};

class Adaptador final : public SensorCelsius {
    const Legado& externo;
public:
    explicit Adaptador(const Legado& proveedor);
    double leer() const override;
};
