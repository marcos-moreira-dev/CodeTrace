# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c lista.rb
# Ejecutar desde el cliente: ruby main.rb

# Lista simple: insertar en cabeza O(1), recorrido O(n).
# Los objetos Nodo son administrados por el recolector de Ruby.
class ListaSimple
  Nodo = Struct.new(:valor, :siguiente)
  def initialize
    @primero = nil
    @cantidad = 0
  end
  attr_reader :cantidad
  def agregar_primero(valor)
    @primero = Nodo.new(valor, @primero)
    @cantidad += 1
  end
  def valores
    r = []
    n = @primero
    until n.nil?
      r << n.valor
      n = n.siguiente
    end
    r
  end
end
