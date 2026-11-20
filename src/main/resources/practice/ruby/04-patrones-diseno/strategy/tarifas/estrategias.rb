# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Un contexto recibe comportamiento como bloque (Strategy idiomático).
# No se obliga al usuario a crear una subclase por cada regla.
class Facturador
  def cobrar(subtotal, &estrategia)
    raise ArgumentError, 'Subtotal negativo' if subtotal.negative?
    raise ArgumentError, 'Falta estrategia' unless estrategia
    estrategia.call(subtotal)
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\strategy\tarifas\main.rb
# Nota: estrategias.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   Normal=100; descuento=90.0
# CodeTrace | FIN EJEMPLO DE CONSOLA
