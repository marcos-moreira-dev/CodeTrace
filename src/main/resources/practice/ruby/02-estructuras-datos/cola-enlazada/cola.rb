# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c cola.rb
# Ejecutar desde el cliente: ruby main.rb

# Cola enlazada: inicio y fin para FIFO con operaciones O(1).
# A diferencia de Array#shift, no hay desplazamiento de índices.
class ColaEnlazada
  Nodo = Struct.new(:valor, :siguiente)
  attr_reader :cantidad
  def initialize
    @primero = @ultimo = nil
    @cantidad = 0
  end
  def encolar(valor)
    n = Nodo.new(valor, nil)
    @ultimo.nil? ? @primero = n : @ultimo.siguiente = n
    @ultimo = n
    @cantidad += 1
  end
  def desencolar
    raise IndexError, 'Cola vacía' if @primero.nil?
    valor = @primero.valor
    @primero = @primero.siguiente
    @ultimo = nil if @primero.nil?
    @cantidad -= 1
    valor
  end
end
