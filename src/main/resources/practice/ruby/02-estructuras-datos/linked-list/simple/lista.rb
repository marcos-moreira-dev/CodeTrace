# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c lista.rb
# Ejecutar desde el cliente: ruby main.rb

# Lista simple: insertar en cabeza O(1), recorrido O(n).
# Los objetos Nodo son administrados por el recolector de Ruby.
class ListaSimple
  Nodo = Struct.new(:valor, :siguiente)
  def initialize
    @primero = nil
    @cantidad = 0
  end
  attr_reader :cantidad
  def agregar_primero(valor)
    @primero = Nodo.new(valor, @primero)
    @cantidad += 1
  end
  def valores
    r = []
    n = @primero
    until n.nil?
      r << n.valor
      n = n.siguiente
    end
    r
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\linked-list\simple\main.rb
# Nota: lista.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   ["C", "B", "A"]
# CodeTrace | FIN EJEMPLO DE CONSOLA
