# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c union_find.rb
# Ejecutar desde el cliente: ruby main.rb

# DSU con compresión de caminos y unión por tamaño.
# O(alfa(n)) amortizado; no devuelve caminos de un grafo.
class UnionFind
  attr_reader :componentes
  def initialize(n)
    raise ArgumentError, 'n' unless n.positive?
    @padre = (0...n).to_a
    @tamanio = Array.new(n, 1)
    @componentes = n
  end
  def raiz(i)
    @padre[i] = raiz(@padre[i]) unless @padre[i] == i
    @padre[i]
  end
  def unir(a, b)
    x = raiz(a); y = raiz(b)
    return false if x == y
    x, y = y, x if @tamanio[x] < @tamanio[y]
    @padre[y] = x
    @tamanio[x] += @tamanio[y]
    @componentes -= 1
    true
  end
  def conectados(a, b); raiz(a) == raiz(b); end
end
