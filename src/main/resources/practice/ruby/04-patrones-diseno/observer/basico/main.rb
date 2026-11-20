# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente Observer, mostrando suscripción, publicación y desuscripción.
#
# Los bloques son objetos de comportamiento en Ruby y el emisor no
# conoce a los consumidores. Usar un token evita que el observador
# tenga que ser una clase. Se recorre una instantánea al publicar.
require_relative 'observador'

def main
  emisor = Emisor.new
  recibidos = []
  token = emisor.suscribir { |valor| recibidos << valor }
  emisor.actualizar(42)
  emisor.desuscribir(token)
  emisor.actualizar(99)
  raise 'Observer incorrecto' unless recibidos == [42]
  p recibidos
end

main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\observer\basico\main.rb
# Salida esperada:
#   [42]
# CodeTrace | FIN EJEMPLO DE CONSOLA
