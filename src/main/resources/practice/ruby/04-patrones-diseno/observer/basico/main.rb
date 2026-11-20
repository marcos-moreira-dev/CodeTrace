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

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
