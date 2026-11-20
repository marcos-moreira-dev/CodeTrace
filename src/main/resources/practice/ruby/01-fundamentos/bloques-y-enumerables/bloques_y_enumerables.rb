# CodeTrace | Referencia: Ruby 3.3
# Compilar/Comprobar: ruby -c ARCHIVO.rb
# Ejecutar: ruby ARCHIVO.rb

# Cliente de bloques, símbolos y Enumerable.
#
# Un bloque pasa comportamiento a un método; select conserva elementos
# que satisfacen una condición, map transforma y reduce acumula.
# En Ruby los bloques son idiomáticos para estas operaciones.
def precios_con_impuesto(precios)
  precios.select { |precio| precio > 0 }
         .map { |precio| (precio * 1.12).round(2) }
end

def main
  valores = [4.5, -1.0, 8.0]
  puts precios_con_impuesto(valores).inspect
end
main if __FILE__ == $PROGRAM_NAME
