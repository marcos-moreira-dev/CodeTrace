# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Excepción propia para un rechazo de dominio; no es Error del parser.
class StockInsuficiente < StandardError
  attr_reader :solicitado, :disponible
  def initialize(solicitado, disponible)
    @solicitado = solicitado
    @disponible = disponible
    super("Solicitado #{solicitado}, disponible #{disponible}")
  end
end

class Inventario
  attr_reader :disponible
  def initialize(stock)
    raise ArgumentError, 'Stock negativo' if stock.negative?
    @disponible = stock
  end
  def despachar(cantidad)
    raise ArgumentError, 'Cantidad' unless cantidad.positive?
    raise StockInsuficiente.new(cantidad, @disponible) if cantidad > @disponible
    @disponible -= cantidad
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\01-fundamentos\excepciones-personalizadas\main.rb
# Nota: inventario.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   Solicitado 10, disponible 3
# CodeTrace | FIN EJEMPLO DE CONSOLA
