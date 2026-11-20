# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c limites.rb
# Ejecutar: ruby main.rb (desde esta carpeta)

# Lower/Upper Bound sobre Array ascendente. O(log n), memoria O(1).
# Lower = primer indice >= objetivo. Upper = primero > objetivo.
module LimitesOrdenados
  module_function
  def lower(a,x)
    lo=0;hi=a.length
    while lo<hi
      m=lo+(hi-lo)/2
      a[m]<x ? lo=m+1 : hi=m
    end
    lo
  end
  def upper(a,x)
    lo=0;hi=a.length
    while lo<hi
      m=lo+(hi-lo)/2
      a[m]<=x ? lo=m+1 : hi=m
    end
    lo
  end
end
