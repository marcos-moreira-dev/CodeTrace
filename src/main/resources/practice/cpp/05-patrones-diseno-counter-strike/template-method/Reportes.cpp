// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — resumen de una ronda virtual en dos formatos.
// Patrón: Template Method.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Reportes.hpp"

namespace cs {

std::string Reporte::generar(const std::string& mapa, int puntos) const {
        // El algoritmo fijo prepara el encabezado y después los datos.
        return encabezado(mapa) + detalle(puntos);
    }

std::string ReporteTexto::encabezado(const std::string& mapa) const { return "Partida " + mapa + ": "; }

std::string ReporteTexto::detalle(int p) const { return "CT=" + std::to_string(p); }

std::string ReporteCsv::encabezado(const std::string& mapa) const { return "mapa,rondasCt;" + mapa + ","; }

std::string ReporteCsv::detalle(int p) const { return std::to_string(p); }

void demo_template() {
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
