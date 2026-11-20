// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <memory>
#include <vector>
#include <algorithm>
#include <cmath>
#include <stdexcept>

/**
 * AVL con unique_ptr y rotaciones mediante std::move.
 * La propiedad del subárbol se transfiere explícitamente, sin
 * crear nodos compartidos. Invariante |altura(izq)-altura(der)|<=1.
 * Insertar O(log n), memoria O(n).
 */
class ArbolAVL{
    struct Nodo{
        int valor,altura=1;
        std::unique_ptr<Nodo> izq,der;
        explicit Nodo(int x):valor(x){}
    };
    std::unique_ptr<Nodo> raiz;
    static int h(const std::unique_ptr<Nodo>& n){return n?n->altura:0;}
    static void actualizar(Nodo& n){n.altura=1+std::max(h(n.izq),h(n.der));}
    static std::unique_ptr<Nodo> rotarDerecha(std::unique_ptr<Nodo> y){
        auto x=std::move(y->izq);
        y->izq=std::move(x->der);
        actualizar(*y);
        x->der=std::move(y);
        actualizar(*x);
        return x;
    }
    static std::unique_ptr<Nodo> rotarIzquierda(std::unique_ptr<Nodo> x){
        auto y=std::move(x->der);
        x->der=std::move(y->izq);
        actualizar(*x);
        y->izq=std::move(x);
        actualizar(*y);
        return y;
    }
    static std::unique_ptr<Nodo> agregar(std::unique_ptr<Nodo> n,int x){
        if(!n)return std::make_unique<Nodo>(x);
        if(x<n->valor)n->izq=agregar(std::move(n->izq),x);
        else if(x>n->valor)n->der=agregar(std::move(n->der),x);
        else return n;
        actualizar(*n);
        int b=h(n->izq)-h(n->der);
        if(b>1){
            if(x>n->izq->valor)n->izq=rotarIzquierda(std::move(n->izq));
            return rotarDerecha(std::move(n));
        }
        if(b< -1){
            if(x<n->der->valor)n->der=rotarDerecha(std::move(n->der));
            return rotarIzquierda(std::move(n));
        }
        return n;
    }
    static void recorrer(const Nodo* n,std::vector<int>& r){
        if(!n)return;
        recorrer(n->izq.get(),r);r.push_back(n->valor);recorrer(n->der.get(),r);
    }
    static int validar(const Nodo* n){
        if(!n)return 0;
        int a=validar(n->izq.get()),b=validar(n->der.get());
        if(std::abs(a-b)>1||n->altura!=1+std::max(a,b))
            throw std::logic_error("AVL desbalanceado");
        return n->altura;
    }
public:
    void insertar(int x){raiz=agregar(std::move(raiz),x);}
    std::vector<int> inorden()const{
        std::vector<int> r;recorrer(raiz.get(),r);return r;
    }
    int altura()const{return validar(raiz.get());}
};
