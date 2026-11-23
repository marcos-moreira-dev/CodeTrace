// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — resumen de una ronda virtual en dos formatos.
// Patrón: Template Method.
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

class Reporte {
public:
    virtual ~Reporte() = default;
    std::string generar(const std::string& mapa, int puntos) const {
        // El algoritmo fijo prepara el encabezado y después los datos.
        return encabezado(mapa) + detalle(puntos);
    }
protected:
    virtual std::string encabezado(const std::string&) const = 0;
    virtual std::string detalle(int) const = 0;
};
class ReporteTexto final : public Reporte {
    std::string encabezado(const std::string& mapa) const override { return "Partida " + mapa + ": "; }
    std::string detalle(int p) const override { return "CT=" + std::to_string(p); }
};
class ReporteCsv final : public Reporte {
    std::string encabezado(const std::string& mapa) const override { return "mapa,rondasCt;" + mapa + ","; }
    std::string detalle(int p) const override { return std::to_string(p); }
};
inline void demo_template() {
    std::cout << "Texto: " << ReporteTexto().generar("de_dust2", 7) << '\n';
    std::cout << "CSV: " << ReporteCsv().generar("de_dust2", 7) << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" template-method
 * Salida esperada:
 *   Texto: Partida de_dust2: CT=7
 *   CSV: mapa,rondasCt;de_dust2,7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
