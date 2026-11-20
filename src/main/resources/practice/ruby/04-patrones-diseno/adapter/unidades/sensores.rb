# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# API antigua: proveedor ajeno cuya forma y unidades no controlamos.
class TermometroLegado
  def fahrenheit
    77.0
  end
end

# Contrato implícito de dominio: cualquier objeto que entienda #celsius.
class AdaptadorCelsius
  def initialize(proveedor)
    @proveedor = proveedor
  end
  def celsius
    (@proveedor.fahrenheit - 32) * 5 / 9
  end
end
