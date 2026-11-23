// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — árbol de mapas de Counter-Strike.
// Patrón: Composite.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <functional>
#include <utility>
#include <stdexcept>
namespace cs {

struct Recurso {
    virtual ~Recurso() = default;
    virtual int contar() const = 0;
};
class ArchivoMapa final : public Recurso {
    std::string nombre_;
public:
    explicit ArchivoMapa(std::string nombre) : nombre_(std::move(nombre)) {}
    int contar() const override { return 1; }
};
class CarpetaMapa final : public Recurso {
    std::vector<std::unique_ptr<Recurso>> hijos_;
public:
    void agregar(std::unique_ptr<Recurso> hijo) { hijos_.push_back(std::move(hijo)); }
    int contar() const override {
        int total = 0;
        // Cada recurso, hoja o carpeta, implementa la misma operación virtual.
        for (const auto& hijo : hijos_) total += hijo->contar();
        return total;
    }
};
inline void demo_composite() {
    CarpetaMapa raiz;
    auto oficiales = std::make_unique<CarpetaMapa>();
    oficiales->agregar(std::make_unique<ArchivoMapa>("de_dust2"));
    oficiales->agregar(std::make_unique<ArchivoMapa>("de_inferno"));
    // move transfiere la propiedad de la subcarpeta al árbol.
    raiz.agregar(std::move(oficiales));
    raiz.agregar(std::make_unique<ArchivoMapa>("cs_office"));
    std::cout << "Mapas registrados: " << raiz.contar() << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" composite
 * Salida esperada:
 *   Mapas registrados: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
