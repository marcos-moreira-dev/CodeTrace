// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "Notificadores.hpp"
#include <memory>

/**
 * Cliente Decorator: añadir una etiqueta sin modificar Consola.
 *
 * La composición de unique_ptr evita copiar objetos polimórficos.
 * std::move transfiere propiedad y garantiza destrucción recursiva
 * cuando el cliente sale del ámbito (RAII). No usamos new/delete
 * manuales ni una subclase para cada combinación de etiquetas.
 */
int main() {
    std::unique_ptr<Notificador> emisor =
        std::make_unique<Prefijo>(std::make_unique<Consola>(), "[INFO] ");
    emisor->enviar("Conexión disponible");
}

/*
 * Salida esperada de consola:
 *   Sin salida de texto; las comprobaciones se superan.
 * Verificacion: terminar sin excepciones inesperadas.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\decorator\notificaciones\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   [INFO] Conexión disponible
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
