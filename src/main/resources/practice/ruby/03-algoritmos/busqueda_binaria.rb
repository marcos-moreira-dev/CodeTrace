# CodeTrace | Referencia: Ruby 3.3.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sintaxis: ruby -c '.\05-algoritmos\busqueda_binaria.rb'
# Ejecutar: ruby '.\05-algoritmos\busqueda_binaria.rb'

def buscar(datos, objetivo)
  izquierda = 0
  derecha = datos.length - 1
  while izquierda <= derecha
    medio = izquierda + (derecha - izquierda) / 2
    return medio if datos[medio] == objetivo
    if datos[medio] < objetivo
      izquierda = medio + 1
    else
      derecha = medio - 1
    end
  end
  -1
end

puts buscar([2, 4, 8, 12, 16], 12)
