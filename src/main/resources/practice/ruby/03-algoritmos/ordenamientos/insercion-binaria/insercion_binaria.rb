# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c insercion_binaria.rb
# Ejecutar: ruby main.rb (desde esta carpeta)

# Binary Insertion Sort: reduce comparaciones a O(n log n),
# pero desplazamientos O(n²) -> tiempo total O(n²). Es estable
# al insertar despues de iguales (upperBound). Auxiliar O(1).
module InsercionBinaria
  module_function
  def ordenar(a)
    (1...a.length).each do |i|
      clave=a[i];lo=0;hi=i
      while lo<hi
        m=lo+(hi-lo)/2
        a[m]<=clave ? lo=m+1 : hi=m
      end
      i.downto(lo+1){|j|a[j]=a[j-1]}
      a[lo]=clave
    end
  end
end
