# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente de excepción propia y preservación del estado.
#
# Ruby normalmente captura errores de StandardError y derivados;
# no debe rescatar indiscriminadamente excepciones fatales del runtime.
# El rechazo por falta de stock se trata cerca de la operación que
# puede recuperar su resultado.
require_relative 'inventario'

def main
  inventario = Inventario.new(5)
  inventario.despachar(2)
  begin
    inventario.despachar(10)
    raise 'Debía fallar'
  rescue StockInsuficiente => error
    raise 'Error de stock' unless error.disponible == 3
    puts error.message
  end
  raise 'Invariante roto' unless inventario.disponible == 3
end

main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
