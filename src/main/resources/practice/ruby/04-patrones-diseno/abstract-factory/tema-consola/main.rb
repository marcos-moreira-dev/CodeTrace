# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Abstract Factory: un tema completo con botón y diálogo.
#
# A diferencia de Factory Method, la fábrica configura FAMILIAS
# de productos, no un único método de creación. Se usan cadenas
# para demostrar contratos de consola sin framework visual.
require_relative 'familias'

def pantalla(f)
  "#{f.boton.pintar} / #{f.dialogo.pintar}"
end

def main
  a = pantalla(FabricaClara.new)
  b = pantalla(FabricaOscura.new)
  raise 'Familias' if a == b || !a.include?('clara') || !b.include?('oscura')
  puts a, b
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola (dos líneas):
#   [ OK ] / Ventana clara
#   < OK > / Ventana oscura
# Se crean familias coherentes por duck typing.

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\abstract-factory\tema-consola\main.rb
# Salida esperada:
#   [ OK ] / Ventana clara
#   < OK > / Ventana oscura
# CodeTrace | FIN EJEMPLO DE CONSOLA
