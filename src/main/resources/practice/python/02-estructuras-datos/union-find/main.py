# CodeTrace | Referencia: Python 3.12
# Comprobar desde COPIA: python -c "import ast,pathlib; ast.parse(pathlib.Path('main.py').read_text(encoding='utf-8'))"
# Ejecutar: python -B main.py

"""Cliente Union-Find para conectividad incremental.

Conectar 0-1 y 1-2 los vuelve transitivamente conectados; unir
0-2 de nuevo no reduce la cantidad de componentes. Es la estructura
que Kruskal utiliza para evitar ciclos durante un MST.
"""
from union_find import UnionFind

def main() -> None:
    uf = UnionFind(5)
    assert uf.unir(0, 1) and uf.unir(1, 2)
    assert not uf.unir(0, 2)
    assert uf.conectados(0, 2) and not uf.conectados(0, 4)
    assert uf.componentes == 3
    print("Componentes:", uf.componentes)

if __name__ == "__main__":
    main()
