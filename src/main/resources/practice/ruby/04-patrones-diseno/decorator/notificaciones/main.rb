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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\decorator\notificaciones\main.rb
# Salida esperada:
#   [INFO] Pedido confirmado
# CodeTrace | FIN EJEMPLO DE CONSOLA
