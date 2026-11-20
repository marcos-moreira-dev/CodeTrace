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
  p s
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
