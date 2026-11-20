// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#pragma once
#include <optional>
#include <string>

/**
 * Command: una petición reversible como objeto.
 * Editor es el receptor; Comando encapsula la operación y undo.
 */
struct Comando{
    virtual ~Comando()=default;
    virtual void ejecutar()=0;
    virtual void deshacer()=0;
};
class Editor{
    std::string texto_;
public:
    void agregar(const std::string& parte);
    void restaurar(std::string anterior);
    const std::string& texto()const;
};
class AgregarTexto final:public Comando{
    Editor& receptor;
    std::string parte;
    std::optional<std::string> anterior;
public:
    AgregarTexto(Editor& receptor,std::string parte);
    void ejecutar()override;
    void deshacer()override;
};
