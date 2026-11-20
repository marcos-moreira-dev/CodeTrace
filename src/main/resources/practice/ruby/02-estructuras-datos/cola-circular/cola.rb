# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cola FIFO de capacidad fija. Índices módulo capacidad, O(1).
class ColaCircular
  def initialize(capacidad)
    raise ArgumentError, 'Capacidad' unless capacidad.positive?
    @datos = Array.new(capacidad)
    @inicio = @siguiente = @cantidad = 0
  end
  def encolar(valor)
    raise IndexError, 'Cola llena' if @cantidad == @datos.length
    @datos[@siguiente] = valor
    @siguiente = (@siguiente + 1) % @datos.length
    @cantidad += 1
  end
  def desencolar
    raise IndexError, 'Cola vacía' if @cantidad.zero?
    valor = @datos[@inicio]
    @datos[@inicio] = nil
    @inicio = (@inicio + 1) % @datos.length
    @cantidad -= 1
    valor
  end
end
