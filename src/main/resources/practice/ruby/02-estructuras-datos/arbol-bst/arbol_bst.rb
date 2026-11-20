# CodeTrace | Referencia: Ruby 3.3
# Comprobar: ruby -c ".\02-estructuras-datos\arbol-bst\arbol_bst.rb"
# Ejecutar: ruby ".\02-estructuras-datos\arbol-bst\arbol_bst.rb"

# Cliente de un BST binario sin rotaciones de balanceo.
#
# Un nodo tiene valor e hijos. Los valores menores se insertan a la
# izquierda, los mayores a la derecha. Buscar e insertar cuestan
# O(h), siendo h la altura (peor caso O(n)).
class Nodo
  attr_accessor :izq, :der
  attr_reader :valor
  def initialize(valor)
    @valor = valor
    @izq = @der = nil
  end
end
class Arbol
  def initialize
    @raiz = nil
  end
  def insertar(valor)
    @raiz = insertar_en(@raiz, valor)
  end
  def inorden(nodo = @raiz, salida = [])
    return salida if nodo.nil?
    inorden(nodo.izq, salida)
    salida << nodo.valor
    inorden(nodo.der, salida)
    salida
  end
  private
  def insertar_en(nodo, valor)
    return Nodo.new(valor) if nodo.nil?
    if valor < nodo.valor
      nodo.izq = insertar_en(nodo.izq, valor)
    elsif valor > nodo.valor
      nodo.der = insertar_en(nodo.der, valor)
    end
    nodo
  end
end
arbol = Arbol.new
[8, 3, 10, 1, 6, 4].each { |x| arbol.insertar(x) }
p arbol.inorden
