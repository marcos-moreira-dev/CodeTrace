// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#include "HashAbierto.hpp"
#include <iostream>
#include <stdexcept>

/**
 * Cliente hash abierto: colisiones en dos casillas, actualización,
 * inexistencia y saturación. C++ optional indica claramente ausencia
 * y evita devolver -1 cuando -1 podría ser un valor legítimo.
 *
 * Borrar requeriría tombstones para no romper el sondeo.
 */
int main(){
    HashAbierto t(2);
    t.poner("A",1);t.poner("B",2);t.poner("A",3);
    if(t.buscar("A")!=3||t.buscar("B")!=2||
       t.buscar("Z").has_value()||t.cantidad()!=2)
        throw std::logic_error("Hash");
    try{t.poner("C",4);throw std::logic_error("Se aceptó tabla llena");}
    catch(const std::overflow_error&){std::cout<<"Tabla llena controlada\n";}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Tabla llena controlada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
