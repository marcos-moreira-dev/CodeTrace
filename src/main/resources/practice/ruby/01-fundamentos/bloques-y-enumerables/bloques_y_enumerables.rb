# CodeTrace | Referencia: Ruby 3.3
# Compilar/Comprobar: ruby -c 01-fundamentos/bloques-y-enumerables/bloques_y_enumerables.rb
# Ejecutar: ruby 01-fundamentos/bloques-y-enumerables/bloques_y_enumerables.rb

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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\01-fundamentos\bloques-y-enumerables\bloques_y_enumerables.rb
# Salida esperada:
#   [5.04, 8.96]
# CodeTrace | FIN EJEMPLO DE CONSOLA
