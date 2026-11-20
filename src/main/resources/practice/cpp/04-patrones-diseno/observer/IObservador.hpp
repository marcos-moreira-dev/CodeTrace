// CodeTrace | Referencia: C++20, proyecto Observer
// Compilar: g++ -std=c++20 04-patrones-diseno/observer/main.cpp -o observer
#pragma once
#include <string>
class IObservador {
public:
    virtual ~IObservador() = default;
    virtual void actualizar(const std::string& evento) = 0;
};
