# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Builder: configuración fluida con producto inmutable.
#
# Ruby dispone de hashes y argumentos con nombre, alternativa válida
# para creaciones simples. Aquí el Builder valida un conjunto de
# opciones y congela las cabeceras antes de entregar el resultado.
require_relative 'solicitud'

def main
  s = SolicitudBuilder.new.ruta('/facturas').metodo('POST')
      .cabecera('Content-Type: application/json').construir
  raise 'Builder' unless s.ruta == '/facturas' && s.cabeceras.length == 1
  puts "#{s.metodo} #{s.ruta} (#{s.cabeceras.length} cabecera)"
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   POST /facturas (1 cabecera)
# La validación del Builder entrega un producto inmutable con una cabecera.

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\builder\solicitud\main.rb
# Salida esperada:
#   POST /facturas (1 cabecera)
# CodeTrace | FIN EJEMPLO DE CONSOLA
