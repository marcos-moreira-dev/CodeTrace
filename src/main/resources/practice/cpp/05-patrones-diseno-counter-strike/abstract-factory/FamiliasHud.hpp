// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — HUD de marcador e inventario con temas compatibles.
// Patrón: Abstract Factory.
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

struct EtiquetaMarcador {
    virtual ~EtiquetaMarcador() = default;
    virtual std::string mostrar(int p) const = 0;
};
struct EtiquetaInventario {
    virtual ~EtiquetaInventario() = default;
    virtual std::string mostrar(const std::string& item) const = 0;
};
class MarcadorTema final : public EtiquetaMarcador {
    std::string tema_;
public:
    explicit MarcadorTema(std::string tema) : tema_(std::move(tema)) {}
    std::string mostrar(int p) const override;
};
class InventarioTema final : public EtiquetaInventario {
    std::string tema_;
public:
    explicit InventarioTema(std::string tema) : tema_(std::move(tema)) {}
    std::string mostrar(const std::string& item) const override;
};
struct FamiliaHud {
    virtual ~FamiliaHud() = default;
    virtual std::unique_ptr<EtiquetaMarcador> marcador() const = 0;
    virtual std::unique_ptr<EtiquetaInventario> inventario() const = 0;
};
class FamiliaTema final : public FamiliaHud {
    std::string tema_;
public:
    explicit FamiliaTema(std::string tema) : tema_(std::move(tema)) {}
    // Cada puntero posee su producto: se destruye automáticamente (RAII).
    std::unique_ptr<EtiquetaMarcador> marcador() const override;
    std::unique_ptr<EtiquetaInventario> inventario() const override;
};
void demo_abstract_factory();

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" abstract-factory
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
