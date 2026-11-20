# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Command con historial LIFO y deshacer.

La UI o cliente solamente construye y despacha Comando; Editor es
el receptor del cambio. Un comando puede guardarse en historial
para deshacerlo sin conocer los detalles internos del editor.
"""
from comandos import Editor, AgregarTexto, Comando

def main() -> None:
    e=Editor()
    historial: list[Comando]=[]
    for texto in ("Hola"," mundo"):
        c=AgregarTexto(e,texto);c.ejecutar();historial.append(c)
    assert e.texto=="Hola mundo"
    historial.pop().deshacer()
    assert e.texto=="Hola"
    print(e.texto)

if __name__ == "__main__":
    main()

# Salida esperada de consola:
#   Hola
# Deshacer el último comando recupera el estado previo del editor.

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   python -B .\04-patrones-diseno\command\deshacer\main.py
# Salida esperada:
#   Hola
# CodeTrace | FIN EJEMPLO DE CONSOLA
