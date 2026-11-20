// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o WSL
// Compilar: g++ -std=c++20 -Wall -Wextra -fPIC -c operaciones.cpp -o operaciones.o
// Ejecutar: solo mediante el cliente main.cpp enlazado.
#include "operaciones.hpp"

namespace didactico {
int sumar(int a, int b) { return a + b; }
int multiplicar(int a, int b) { return a * b; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\01-fundamentos\compilacion-y-enlazado\biblioteca-estatica-dinamica\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: operaciones.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Suma=7, producto=12
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
