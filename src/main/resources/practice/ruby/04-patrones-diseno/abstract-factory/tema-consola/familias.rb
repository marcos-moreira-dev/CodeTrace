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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\abstract-factory\tema-consola\main.rb
# Nota: familias.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   [ OK ] / Ventana clara
#   < OK > / Ventana oscura
# CodeTrace | FIN EJEMPLO DE CONSOLA
