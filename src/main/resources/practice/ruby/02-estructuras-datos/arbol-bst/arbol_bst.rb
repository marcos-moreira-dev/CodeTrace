# CodeTrace | Referencia: Ruby 3.3
# Comprobar: ruby -c ".\02-estructuras-datos\arbol-bst\arbol_bst.rb"
# Ejecutar: ruby ".\02-estructuras-datos\arbol-bst\arbol_bst.rb"

# Implementación de BST sin balanceo; cliente en main.rb.
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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\arbol-bst\main.rb
# Nota: arbol_bst.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   [1, 3, 4, 6, 8, 10]
# CodeTrace | FIN EJEMPLO DE CONSOLA
