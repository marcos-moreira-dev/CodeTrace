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
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
