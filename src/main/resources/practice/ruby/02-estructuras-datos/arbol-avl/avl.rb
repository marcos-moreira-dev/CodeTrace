# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c avl.rb
# Ejecutar desde el cliente: ruby main.rb

# AVL: BST autoequilibrado con rotaciones, inserción O(log n).
class ArbolAVL
  Nodo = Struct.new(:valor, :izq, :der, :altura)
  def initialize
    @raiz = nil
  end
  def insertar(valor)
    @raiz = agregar(@raiz, valor)
  end
  def inorden
    salida = []
    visitar = lambda do |n|
      next if n.nil?
      visitar.call(n.izq)
      salida << n.valor
      visitar.call(n.der)
    end
    visitar.call(@raiz)
    salida
  end
  def validar
    medir = lambda do |n|
      next 0 if n.nil?
      a = medir.call(n.izq)
      b = medir.call(n.der)
      raise 'Desbalance AVL' if (a-b).abs > 1 || n.altura != 1 + [a,b].max
      n.altura
    end
    medir.call(@raiz)
  end
  private
  def altura(n); n.nil? ? 0 : n.altura; end
  def actualizar(n); n.altura = 1 + [altura(n.izq), altura(n.der)].max; end
  def derecha(y)
    x = y.izq
    y.izq = x.der
    x.der = y
    actualizar(y); actualizar(x)
    x
  end
  def izquierda(x)
    y = x.der
    x.der = y.izq
    y.izq = x
    actualizar(x); actualizar(y)
    y
  end
  def agregar(n, valor)
    return Nodo.new(valor, nil, nil, 1) if n.nil?
    if valor < n.valor
      n.izq = agregar(n.izq, valor)
    elsif valor > n.valor
      n.der = agregar(n.der, valor)
    else
      return n
    end
    actualizar(n)
    b = altura(n.izq) - altura(n.der)
    if b > 1
      n.izq = izquierda(n.izq) if valor > n.izq.valor
      return derecha(n)
    end
    if b < -1
      n.der = derecha(n.der) if valor < n.der.valor
      return izquierda(n)
    end
    n
  end
end
