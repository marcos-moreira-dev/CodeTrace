# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c solicitud.rb
# Ejecutar desde el cliente: ruby main.rb

# Builder construye un producto de forma incremental y validada.
# Ruby Data.define (Ruby 3.2+) representa un registro por valor.
Solicitud = Data.define(:ruta, :metodo, :cabeceras)

class SolicitudBuilder
  def initialize
    @ruta = ''
    @metodo = 'GET'
    @cabeceras = []
  end
  def ruta(valor); @ruta = valor; self; end
  def metodo(valor); @metodo = valor; self; end
  def cabecera(valor); @cabeceras << valor; self; end
  def construir
    raise ArgumentError, 'Solicitud' unless @ruta.start_with?('/') && !@metodo.empty?
    Solicitud.new(@ruta, @metodo, @cabeceras.dup.freeze)
  end
end
