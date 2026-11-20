// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Comandos.hpp"
#include <iostream>
#include <memory>
#include <stdexcept>
#include <vector>

/**
 * Cliente Command con historial LIFO de unique_ptr<Comando>.
 *
 * El comando contiene un Editor& no propietario: el Editor debe
 * vivir más que los comandos. Un objeto puede deshacer porque
 * almacena el estado anterior, a diferencia de un callback puro.
 */
int main(){
    Editor editor;
    std::vector<std::unique_ptr<Comando>> historial;
    for(const char* parte:{"Hola"," mundo"}){
        auto c=std::make_unique<AgregarTexto>(editor,parte);
        c->ejecutar();
        historial.push_back(std::move(c));
    }
    if(editor.texto()!="Hola mundo")throw std::logic_error("Command");
    historial.back()->deshacer();
    historial.pop_back();
    if(editor.texto()!="Hola")throw std::logic_error("Undo");
    std::cout<<editor.texto()<<'\n';
}

/*
 * Salida esperada de consola:
 *   Se muestran los datos o el resultado calculado del ejemplo.
 * Verificacion: terminar sin excepciones inesperadas.
 */
