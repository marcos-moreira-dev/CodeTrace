# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Template Method: el método plantilla fija validación
# y secuencia; las subclases modifican pasos mediante herencia.
#
# Contrastar con Strategy, que recibe comportamiento a través de
# composición (bloque o callable) en lugar de especializar una clase.
require_relative 'reporte'

def main
  texto = ReporteTexto.new
  csv = ReporteCsv.new
  raise 'Texto' unless texto.ejecutar('ventas',15).include?('Total: 15')
  raise 'CSV' unless csv.ejecutar('ventas',15).include?('ventas,15')
  puts texto.ejecutar('ventas',15), csv.ejecutar('ventas',15)
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
