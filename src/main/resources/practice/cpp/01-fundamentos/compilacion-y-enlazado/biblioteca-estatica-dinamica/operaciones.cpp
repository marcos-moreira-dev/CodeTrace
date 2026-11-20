// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o WSL
// Compilar: g++ -std=c++20 -Wall -Wextra -fPIC -c operaciones.cpp -o operaciones.o
// Ejecutar: solo mediante el cliente main.cpp enlazado.
#include "operaciones.hpp"

namespace didactico {
int sumar(int a, int b) { return a + b; }
int multiplicar(int a, int b) { return a * b; }
}
