// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\01-fundamentos\semantica-movimiento\semantica_movimiento.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <iostream>
#include <memory>
#include <utility>
#include <vector>

/**
 * Cliente de move semantics y propiedad de recursos.
 *
 * Un unique_ptr no se copia porque dos propietarios exclusivos serían
 * contradictorios. std::move convierte la expresión en una que permite
 * transferir propiedad; después del movimiento, el origen queda vacío.
 * No significa mover bytes siempre ni libera por sí solo la memoria.
 */
struct Tarea { int id; };
int main() {
    auto propietaria = std::make_unique<Tarea>(Tarea{42});
    std::vector<std::unique_ptr<Tarea>> cola;
    cola.push_back(std::move(propietaria));
    std::cout << "Origen vacío: " << std::boolalpha << !propietaria << '\n';
    std::cout << "Destino: " << cola.front()->id << '\n';
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\01-fundamentos\semantica-movimiento\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Origen vacío: true
 *   Destino: 42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
