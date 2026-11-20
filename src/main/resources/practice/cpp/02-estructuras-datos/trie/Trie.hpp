// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <map>
#include <memory>
#include <string_view>

/**
 * Trie por caracteres, cada nodo posee hijos vía unique_ptr.
 * Insertar/consulta O(L) para L caracteres (std::map agrega
 * factor log del alfabeto). No compara palabras completas
 * en cada nodo como un BST de strings.
 */
class Trie{
    struct Nodo{
        std::map<char,std::unique_ptr<Nodo>> hijos;
        bool final=false;
    };
    Nodo raiz;
    const Nodo* nodo(std::string_view texto)const{
        auto n=&raiz;
        for(char c:texto){
            const auto it=n->hijos.find(c);
            if(it==n->hijos.end())return nullptr;
            n=it->second.get();
        }
        return n;
    }
public:
    void insertar(std::string_view palabra){
        auto n=&raiz;
        for(char c:palabra){
            auto& hijo=n->hijos[c];
            if(!hijo)hijo=std::make_unique<Nodo>();
            n=hijo.get();
        }
        n->final=true;
    }
    bool contiene(std::string_view palabra)const{
        auto n=nodo(palabra);return n&&n->final;
    }
    bool prefijo(std::string_view texto)const{return nodo(texto)!=nullptr;}
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\trie\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Trie.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Trie de prefijos OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
