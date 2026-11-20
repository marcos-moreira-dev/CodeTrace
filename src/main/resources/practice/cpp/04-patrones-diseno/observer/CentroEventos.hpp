// CodeTrace | Referencia: C++20, proyecto Observer
// Compilar: g++ -std=c++20 04-patrones-diseno/observer/main.cpp -o observer
#pragma once
#include "IObservador.hpp"
#include <string>
#include <vector>
class CentroEventos {
    std::vector<IObservador*> observadores_;
public:
    void registrar(IObservador& o) { observadores_.push_back(&o); }
    void publicar(const std::string& evento) {
        for (auto* o : observadores_) o->actualizar(evento);
    }
};
