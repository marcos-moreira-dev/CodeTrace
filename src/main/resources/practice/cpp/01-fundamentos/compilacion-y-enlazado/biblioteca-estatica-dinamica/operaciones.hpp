// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o WSL
// Compilar: sh compilar.sh
// Esta cabecera se utiliza por operaciones.cpp y main.cpp.
#pragma once

namespace didactico {
    // API C++ del dominio: la definición se compila en otra unidad.
    int sumar(int a, int b);
    int multiplicar(int a, int b);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\01-fundamentos\compilacion-y-enlazado\biblioteca-estatica-dinamica\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: operaciones.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Suma=7, producto=12
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
