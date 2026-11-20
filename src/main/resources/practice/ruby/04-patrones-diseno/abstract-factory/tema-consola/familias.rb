# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c familias.rb
# Ejecutar desde el cliente: ruby main.rb

# Abstract Factory produce dos componentes de un tema.
# Ruby usa duck typing en lugar de interfaces nominales Java.
class Texto
  def initialize(contenido); @contenido = contenido; end
  def pintar; @contenido; end
end

class FabricaClara
  def boton; Texto.new('[ OK ]'); end
  def dialogo; Texto.new('Ventana clara'); end
end

class FabricaOscura
  def boton; Texto.new('< OK >'); end
  def dialogo; Texto.new('Ventana oscura'); end
end
