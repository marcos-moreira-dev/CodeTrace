# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente Decorator: extender una notificación sin modificar Consola.
#
# La composición permite apilar envoltorios conservando el contrato
# enviar; las clases no se duplican para cada combinación de etiquetas.
# En Ruby esto funciona por métodos compatibles, sin declaración formal.
require_relative 'notificadores'

def main
  emisor = ConPrefijo.new(Consola.new, '[INFO] ')
  emisor.enviar('Pedido confirmado')
end

main if __FILE__ == $PROGRAM_NAME
