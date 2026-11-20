# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Pila LIFO con Array, nativa de Ruby. push/pop O(1) amortizado.
class Pila
  def initialize
    @datos = []
  end
  def apilar(valor)
    @datos.push(valor)
  end
  def desapilar
    raise IndexError, 'Pila vacía' if @datos.empty?
    @datos.pop
  end
  def cantidad
    @datos.length
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\pila\main.rb
# Nota: pila.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   Pila correcta
# CodeTrace | FIN EJEMPLO DE CONSOLA
