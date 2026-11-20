// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL); .\ejemplo.exe (Windows)
// Contrato .hpp, implementacion .cpp y cliente main.cpp.

#pragma once
#include <vector>
/**
 * Binary Insertion Sort: comparaciones O(n log n), pero desplazar
 * datos cuesta O(n²); total sigue O(n²), memoria O(1), estable.
 */
void insercionBinaria(std::vector<int>& datos);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\ordenamientos\insercion-binaria\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: InsercionBinaria.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
