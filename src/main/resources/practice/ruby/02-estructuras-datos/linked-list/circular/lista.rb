# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c lista.rb
# Ejecutar desde el cliente: ruby main.rb

# Linked List simple CIRCULAR: tail.next enlaza con head.
# Insertar/rotar O(1), recorrer O(n) con contador.
class ListaCircular
  Nodo = Struct.new(:valor, :siguiente)
  def initialize
    @ultimo = nil
    @cantidad = 0
  end

  def agregar(valor)
    n = Nodo.new(valor, nil)
    if @ultimo.nil?
      n.siguiente = n
    else
      n.siguiente = @ultimo.siguiente
      @ultimo.siguiente = n
    end
    @ultimo = n
    @cantidad += 1
  end

  def rotar
    @ultimo = @ultimo.siguiente unless @ultimo.nil?
  end

  def valores
    return [] if @ultimo.nil?
    n = @ultimo.siguiente
    Array.new(@cantidad) do
      dato = n.valor
      n = n.siguiente
      dato
    end
  end
end
