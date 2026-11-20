# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c trie.rb
# Ejecutar desde el cliente: ruby main.rb

# Trie por caracteres Unicode Ruby; cada nodo contiene Hash de hijos.
# Costo promedio O(L) en longitud de palabra, almacenamiento por nodos.
class Trie
  Nodo = Struct.new(:hijos, :terminal)
  def initialize
    @raiz = Nodo.new({}, false)
  end
  def insertar(texto)
    n = @raiz
    texto.each_char do |c|
      n.hijos[c] ||= Nodo.new({}, false)
      n = n.hijos[c]
    end
    n.terminal = true
  end
  def contiene(texto)
    n = buscar(texto)
    !n.nil? && n.terminal
  end
  def prefijo(texto)
    !buscar(texto).nil?
  end
  private
  def buscar(texto)
    n = @raiz
    texto.each_char do |c|
      n = n.hijos[c]
      return nil if n.nil?
    end
    n
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\trie\main.rb
# Nota: trie.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   Trie correcto
# CodeTrace | FIN EJEMPLO DE CONSOLA
