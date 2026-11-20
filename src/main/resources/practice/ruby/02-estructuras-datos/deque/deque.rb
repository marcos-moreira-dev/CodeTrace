# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c deque.rb
# Ejecutar desde el cliente: ruby main.rb

# Deque por lista doble: agregar y retirar en ambos extremos O(1).
class DequeCurso
  Nodo = Struct.new(:valor, :anterior, :siguiente)
  def initialize
    @primero = @ultimo = nil
  end
  def agregar_primero(valor)
    n = Nodo.new(valor, nil, @primero)
    @primero.nil? ? @ultimo = n : @primero.anterior = n
    @primero = n
  end
  def agregar_ultimo(valor)
    n = Nodo.new(valor, @ultimo, nil)
    @ultimo.nil? ? @primero = n : @ultimo.siguiente = n
    @ultimo = n
  end
  def quitar_primero
    raise IndexError, 'Deque vacío' if @primero.nil?
    valor = @primero.valor
    @primero = @primero.siguiente
    @primero.nil? ? @ultimo = nil : @primero.anterior = nil
    valor
  end
  def quitar_ultimo
    raise IndexError, 'Deque vacío' if @ultimo.nil?
    valor = @ultimo.valor
    @ultimo = @ultimo.anterior
    @ultimo.nil? ? @primero = nil : @ultimo.siguiente = nil
    valor
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\deque\main.rb
# Nota: deque.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   ["A", "C", "B"]
# CodeTrace | FIN EJEMPLO DE CONSOLA
