# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c heap.rb
# Ejecutar desde el cliente: ruby main.rb

# Max heap binario sobre Array; padre>=hijos, O(log n) push/pop.
class HeapMaximo
  def initialize; @a = []; end
  def insertar(valor)
    @a << valor
    i = @a.length - 1
    while i.positive?
      p = (i-1)/2
      break if @a[p] >= @a[i]
      @a[p], @a[i] = @a[i], @a[p]
      i = p
    end
  end
  def extraer
    raise IndexError, 'Heap vacío' if @a.empty?
    max = @a.first
    ultimo = @a.pop
    unless @a.empty?
      @a[0] = ultimo
      i = 0
      loop do
        m=i; izq=2*i+1; der=izq+1
        m=izq if izq < @a.length && @a[izq] > @a[m]
        m=der if der < @a.length && @a[der] > @a[m]
        break if m==i
        @a[i], @a[m] = @a[m], @a[i]
        i=m
      end
    end
    max
  end
end
