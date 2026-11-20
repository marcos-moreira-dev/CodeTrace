# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente Strategy con bloques como objetos de comportamiento.
#
# Ruby permite pasar bloques y convertirlos en Proc. Ese mecanismo
# cumple el rol de estrategia para algoritmos pequeños y sin estado.
# Un objeto dedicado valdría la pena para configurar una política
# más compleja, como reglas distintas de impuestos regionales.
require_relative 'estrategias'

def main
  facturador = Facturador.new
  normal = facturador.cobrar(100) { |total| total }
  descuento = facturador.cobrar(100) { |total| total * 0.9 }
  raise 'Strategy incorrecta' unless [normal, descuento] == [100, 90.0]
  puts "Normal=#{normal}; descuento=#{descuento}"
end

main if __FILE__ == $PROGRAM_NAME
