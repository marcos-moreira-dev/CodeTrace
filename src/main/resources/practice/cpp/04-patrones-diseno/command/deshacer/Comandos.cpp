// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Comandos.hpp"
#include <stdexcept>
#include <utility>

void Editor::agregar(const std::string& parte){texto_+=parte;}
void Editor::restaurar(std::string anterior){texto_=std::move(anterior);}
const std::string& Editor::texto()const{return texto_;}
AgregarTexto::AgregarTexto(Editor& e,std::string parte)
    :receptor(e),parte(std::move(parte)){}
void AgregarTexto::ejecutar(){
    if(anterior)throw std::logic_error("Ya ejecutado");
    anterior=receptor.texto();
    receptor.agregar(parte);
}
void AgregarTexto::deshacer(){
    if(!anterior)throw std::logic_error("No ejecutado");
    receptor.restaurar(*anterior);
    anterior.reset();
}
