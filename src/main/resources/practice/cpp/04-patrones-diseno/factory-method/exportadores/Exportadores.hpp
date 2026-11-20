// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#pragma once
#include <memory>
#include <string>

/** Producto abstracto: el cliente depende de esta interfaz. */
struct Exportador {
    virtual ~Exportador() = default;
    virtual std::string exportar(const std::string& documento) const = 0;
};

/**
 * Creador GoF con método de fábrica polimórfico.
 * preparar() contiene lógica común, crear() es el punto de extensión.
 * No es la mera función fábrica que decide con un switch.
 */
class Creador {
protected:
    virtual std::unique_ptr<Exportador> crear() const = 0;
public:
    virtual ~Creador() = default;
    std::string preparar(const std::string& documento) const;
};

class CreadorTexto final : public Creador {
protected:
    std::unique_ptr<Exportador> crear() const override;
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\factory-method\exportadores\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Exportadores.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   TXT: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
