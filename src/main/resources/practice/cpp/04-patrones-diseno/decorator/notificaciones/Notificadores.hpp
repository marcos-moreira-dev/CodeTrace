// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#pragma once
#include <memory>
#include <string>

/** Componente base, con destructor virtual para destrucción polimórfica. */
struct Notificador {
    virtual ~Notificador() = default;
    virtual void enviar(const std::string& mensaje) const = 0;
};

struct Consola final : Notificador {
    void enviar(const std::string& mensaje) const override;
};

/**
 * Decorator guarda un unique_ptr y posee el objeto envuelto.
 * Se muestra RAII: destruir el decorador destruye toda la cadena.
 */
class Prefijo final : public Notificador {
    std::unique_ptr<Notificador> interno;
    std::string etiqueta;
public:
    Prefijo(std::unique_ptr<Notificador> interno, std::string etiqueta);
    void enviar(const std::string& mensaje) const override;
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\decorator\notificaciones\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Notificadores.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   [INFO] Conexión disponible
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
