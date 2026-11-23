// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — árbol de mapas de Counter-Strike.
// Patrón: Composite.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Cabecera: declara la interfaz; las operaciones principales se definen en el .cpp.
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
    int contar() const override;
};
class CarpetaMapa final : public Recurso {
    std::vector<std::unique_ptr<Recurso>> hijos_;
public:
    void agregar(std::unique_ptr<Recurso> hijo);
    int contar() const override;
};
void demo_composite();

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
