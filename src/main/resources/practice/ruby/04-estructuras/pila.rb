# CodeTrace | Referencia: Ruby 3.3.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sintaxis: ruby -c '.\04-estructuras\pila.rb'
# Ejecutar: ruby '.\04-estructuras\pila.rb'

class Pila
  def initialize
    @datos = []
  end

  def apilar(valor)
    @datos.push(valor)
  end

  def desapilar
    raise "Pila vacía" if @datos.empty?
    @datos.pop
  end
end

pila = Pila.new
pila.apilar(12)
pila.apilar(8)
puts pila.desapilar
