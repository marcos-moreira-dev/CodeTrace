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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\03-algoritmos\busqueda-rangos\main.rb
# Nota: limites.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   Valor 3: [1, 4)
#   Insertar 4 en indice: 4
# CodeTrace | FIN EJEMPLO DE CONSOLA
