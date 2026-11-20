# CodeTrace | Referencia: Ruby 3.3
# Compilar/Comprobar: ruby -c ARCHIVO.rb
# Ejecutar: ruby ARCHIVO.rb

# Cliente de módulos y mixins.
#
# Un módulo Ruby puede agrupar comportamiento que distintas clases
# reutilizan sin introducir una jerarquía de herencia adicional.
# Se enfatiza el contrato de describir, no la sintaxis de Java.
module Descriptible
  def describir
    "Código: #{codigo}"
  end
end
class Sensor
  include Descriptible
  attr_reader :codigo
  def initialize(codigo)
    @codigo = codigo
  end
end
puts Sensor.new("LM35").describir
