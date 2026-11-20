# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c reporte.rb
# Ejecutar desde el cliente: ruby main.rb

# Template Method: ejecutar establece el orden fijo de los pasos.
# La herencia permite especializar encabezado y detalle; no requiere
# un framework. En Ruby no existe un 'final' efectivo como en Java.
class ProcesoReporte
  def ejecutar(titulo, total)
    raise ArgumentError, 'Reporte' if titulo.empty? || total.negative?
    encabezado(titulo) + detalle(total) + pie
  end
  protected
  def encabezado(_titulo); raise NotImplementedError; end
  def detalle(_total); raise NotImplementedError; end
  def pie; "\n"; end
end

class ReporteTexto < ProcesoReporte
  protected
  def encabezado(titulo); "== #{titulo} ==\n"; end
  def detalle(total); "Total: #{total}"; end
end

class ReporteCsv < ProcesoReporte
  protected
  def encabezado(_titulo); "titulo,total\n"; end
  def detalle(total); "ventas,#{total}"; end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\template-method\reportes\main.rb
# Nota: reporte.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   == ventas ==
#   Total: 15
#   titulo,total
#   ventas,15
# CodeTrace | FIN EJEMPLO DE CONSOLA
