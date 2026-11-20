# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente de Factory Method, distinto de una función fábrica.
#
# La clase Creador concentra el algoritmo preparar y delega crear
# a una subclase. En Ruby los métodos son dinámicos, pero el contrato
# estructural sigue siendo claro. El cliente sólo elige el creador.
# Una fábrica simple sería más apropiada para un único producto.
require_relative 'fabrica'

def main
  creador = CreadorTexto.new
  salida = creador.preparar('Factura 001')
  raise 'Error de fábrica' unless salida == 'TXT: Factura 001'
  puts salida
end

main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\factory-method\exportadores\main.rb
# Salida esperada:
#   TXT: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
