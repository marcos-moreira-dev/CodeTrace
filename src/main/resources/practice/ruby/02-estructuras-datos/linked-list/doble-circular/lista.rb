# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c lista.rb
# Ejecutar desde el cliente: ruby main.rb

# Lista doble circular: head.prev=tail y tail.next=head.
# El recorrido se limita por cantidad para no entrar en bucle infinito.
class ListaDobleCircular
  Nodo = Struct.new(:valor, :anterior, :siguiente)
  def initialize
    @ultimo = nil
    @cantidad = 0
  end
  def agregar(valor)
    n = Nodo.new(valor, nil, nil)
    if @ultimo.nil?
      n.anterior = n
      n.siguiente = n
    else
      primero = @ultimo.siguiente
      n.anterior = @ultimo
      n.siguiente = primero
      @ultimo.siguiente = n
      primero.anterior = n
    end
    @ultimo = n
    @cantidad += 1
  end
  def valores(inverso = false)
    return [] if @ultimo.nil?
    n = inverso ? @ultimo : @ultimo.siguiente
    Array.new(@cantidad) do
      dato = n.valor
      n = inverso ? n.anterior : n.siguiente
      dato
    end
  end
end
