# CodeTrace | Referencia: Ruby 3.3.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sintaxis: ruby -c '.\04-patrones-diseno\observador.rb'
# Ejecutar: ruby '.\04-patrones-diseno\observador.rb'

class Emisor
  def initialize
    @oyentes = []
  end

  def suscribir(&callback)
    @oyentes << callback
  end

  def actualizar(valor)
    @oyentes.each { |callback| callback.call(valor) }
  end
end

emisor = Emisor.new
emisor.suscribir { |valor| puts "Cambio: #{valor}" }
emisor.actualizar(42)
