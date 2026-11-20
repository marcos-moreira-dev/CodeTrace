// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Elementos.hpp"
#include <numeric>
#include <stdexcept>
#include <utility>

Archivo::Archivo(std::string nombre,int t):nombre_(std::move(nombre)),tamanio_(t){
    if(t<0)throw std::invalid_argument("Tamaño negativo");
}
int Archivo::bytes()const{return tamanio_;}
Carpeta::Carpeta(std::string nombre):nombre_(std::move(nombre)){}
void Carpeta::agregar(std::unique_ptr<Elemento> e){
    if(!e||e.get()==this)throw std::invalid_argument("Ciclo directo o hijo nulo");
    hijos.push_back(std::move(e));
}
int Carpeta::bytes()const{
    int total=0;
    for(const auto& e:hijos)total+=e->bytes();
    return total;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\composite\arbol-archivos\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Elementos.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Bytes: 220
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
