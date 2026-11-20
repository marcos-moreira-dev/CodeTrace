// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#pragma once
#include <memory>
#include <string>
#include <vector>

/**
 * Composite: hojas y grupos implementan bytes().
 * La carpeta posee sus hijos mediante unique_ptr<Elemento>.
 */
class Elemento {
public:
    virtual ~Elemento()=default;
    virtual int bytes()const=0;
};
class Archivo final:public Elemento{
    std::string nombre_;
    int tamanio_;
public:
    Archivo(std::string nombre,int tamanio);
    int bytes()const override;
};
class Carpeta final:public Elemento{
    std::string nombre_;
    std::vector<std::unique_ptr<Elemento>> hijos;
public:
    explicit Carpeta(std::string nombre);
    void agregar(std::unique_ptr<Elemento> hijo);
    int bytes()const override;
};
