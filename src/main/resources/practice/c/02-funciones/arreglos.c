#include <stdio.h>

int sumar(const int datos[], int cantidad) {
    int resultado = 0;
    for (int i = 0; i < cantidad; i++) {
        resultado += datos[i];
    }
    return resultado;
}

int main(void) {
    int datos[] = {2, 3, 5};
    printf("%d\n", sumar(datos, 3));
    return 0;
}
