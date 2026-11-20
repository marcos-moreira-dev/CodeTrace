// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 -Wall -Wextra 01-fundamentos/raii-y-propiedad/raii_y_propiedad.cpp -o app.exe
// Ejecutar: ./app.exe

#include <iostream>
#include <memory>
#include <vector>
/**
 * Cliente de RAII: el destructor gestiona la liberación de recursos.
 * unique_ptr expresa propiedad exclusiva y evita el par manual
 * malloc/free de C. Un vector almacena sus elementos sin new/delete.
 */
struct Sensor {
    int lectura;
    explicit Sensor(int x) : lectura(x) {}
    ~Sensor() { std::cout << "Sensor liberado\n"; }
};
int main() {
    auto sensor = std::make_unique<Sensor>(42);
    const std::vector<int> muestras{3, 5, 8};
    std::cout << sensor->lectura << ", " << muestras.at(1) << '\n';
}
