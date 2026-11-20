# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c lista.rb
# Ejecutar desde el cliente: ruby main.rb

# Linked List DOBLE: nodos con anterior y siguiente.
# Agregar/quitar al final O(1), recorrer O(n).
class ListaDoble
  Nodo = Struct.new(:valor, :anterior, :siguiente)
  def initialize
    @primero = @ultimo = nil
    @cantidad = 0
  end
  attr_reader :cantidad

  def agregar_final(valor)
    n = Nodo.new(valor, @ultimo, nil)
    if @ultimo.nil?
      @primero = n
    else
      @ultimo.siguiente = n
    end
    @ultimo = n
    @cantidad += 1
  end

  def quitar_final
    raise IndexError, 'Lista vacía' if @ultimo.nil?
    v = @ultimo.valor
    @ultimo = @ultimo.anterior
    @ultimo.nil? ? @primero = nil : @ultimo.siguiente = nil
    @cantidad -= 1
    v
  end

  def valores(inverso = false)
    salida = []
    n = inverso ? @ultimo : @primero
    until n.nil?
      salida << n.valor
      n = inverso ? n.anterior : n.siguiente
    end
    salida
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\linked-list\doble\main.rb
# Nota: lista.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   ["A", "B"]
# CodeTrace | FIN EJEMPLO DE CONSOLA
