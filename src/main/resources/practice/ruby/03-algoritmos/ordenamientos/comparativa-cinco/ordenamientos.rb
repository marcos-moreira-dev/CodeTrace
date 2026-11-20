# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c ordenamientos.rb
# Ejecutar: ruby main.rb (desde esta carpeta)

# Cinco sorting clasicos; todos modifican Array in-place.
#
# Insercion/Burbuja: mejor O(n), peor O(n²), estables, O(1) memoria.
# Merge: O(n log n), estable (<= desempata izquierda), O(n) auxiliar.
# Heap: O(n log n), O(1) auxiliar, no estable.
# Quick pivote final: esperado O(n log n), peor O(n²), no estable.
module Ordenamientos
  module_function
  def insercion(a)
    (1...a.length).each do |i|
      clave=a[i];j=i-1
      while j>=0&&a[j]>clave
        a[j+1]=a[j];j-=1
      end
      a[j+1]=clave
    end
  end
  def burbuja(a)
    limite=a.length
    while limite>1
      ultimo=0
      (1...limite).each do |j|
        if a[j-1]>a[j]
          a[j-1],a[j]=a[j],a[j-1];ultimo=j
        end
      end
      limite=ultimo
    end
  end
  def mezcla(a)
    temp=Array.new(a.length)
    ordenar=lambda do |lo,hi|
      next if hi-lo<=1
      m=lo+(hi-lo)/2
      ordenar.call(lo,m);ordenar.call(m,hi)
      i=lo;j=m
      (lo...hi).each do |k|
        if j==hi||(i<m&&a[i]<=a[j])
          temp[k]=a[i];i+=1
        else
          temp[k]=a[j];j+=1
        end
      end
      a[lo...hi]=temp[lo...hi]
    end
    ordenar.call(0,a.length)
  end
  def heap(a)
    hundir=lambda do |n,inicio|
      i=inicio
      loop do
        mayor=i;iz=2*i+1;de=iz+1
        mayor=iz if iz<n&&a[iz]>a[mayor]
        mayor=de if de<n&&a[de]>a[mayor]
        break if mayor==i
        a[i],a[mayor]=a[mayor],a[i];i=mayor
      end
    end
    (a.length/2-1).downto(0){|i|hundir.call(a.length,i)}
    (a.length-1).downto(1) do |fin|
      a[0],a[fin]=a[fin],a[0]
      hundir.call(fin,0)
    end
  end
  def rapido(a)
    ordenar=lambda do |lo,hi|
      next if lo>=hi
      p=a[hi];i=lo
      (lo...hi).each do |j|
        if a[j]<=p
          a[i],a[j]=a[j],a[i];i+=1
        end
      end
      a[i],a[hi]=a[hi],a[i]
      ordenar.call(lo,i-1);ordenar.call(i+1,hi)
    end
    ordenar.call(0,a.length-1)
  end
end
