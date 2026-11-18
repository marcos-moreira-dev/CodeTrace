#include <iostream>

class Contador {
    int valor = 0;
public:
    void incrementar() { ++valor; }
    int obtener() const { return valor; }
};

int main() {
    Contador contador;
    contador.incrementar();
    std::cout << contador.obtener() << '\n';
}
