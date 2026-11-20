# CodeTrace | Referencia: Ruby 3.3.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sintaxis: ruby -c '.\02-estructuras-datos\pila\pila.rb'
# Ejecutar: ruby '.\02-estructuras-datos\pila\pila.rb'

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
