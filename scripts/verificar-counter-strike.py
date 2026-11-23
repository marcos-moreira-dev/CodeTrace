#!/usr/bin/env python3
"""Compila y ejecuta los 13 patrones y el integrador en Python, C# y C++.
Las fuentes se conservan intactas: los artefactos viven en un temporal.
"""
from pathlib import Path
import json
import os
import shutil
import subprocess
import sys
import tempfile

ROOT = Path(__file__).resolve().parents[1]
PRACTICE = ROOT / "src/main/resources/practice"
TRACK = "05-patrones-diseno-counter-strike"
MANIFEST = json.loads((ROOT / "scripts/salidas_practica.json").read_text(encoding="utf-8"))
PATTERNS = [
    ("abstract-factory", "abstract_factory/familias.py", "abstract-factory/FamiliasHud.cs", "abstract-factory/FamiliasHud.hpp"),
    ("adapter", "adapter/ping.py", "adapter/Ping.cs", "adapter/Ping.hpp"),
    ("builder", "builder/sala.py", "builder/Sala.cs", "builder/Sala.hpp"),
    ("chain-of-responsibility", "chain_of_responsibility/cadena.py", "chain-of-responsibility/Cadena.cs", "chain-of-responsibility/Cadena.hpp"),
    ("command", "command/marcador.py", "command/Comandos.cs", "command/Comandos.hpp"),
    ("composite", "composite/mapas.py", "composite/Recursos.cs", "composite/Recursos.hpp"),
    ("decorator", "decorator/avisos.py", "decorator/Decoradores.cs", "decorator/Avisos.hpp"),
    ("factory", "factory/chat.py", "factory/Chat.cs", "factory/Chat.hpp"),
    ("factory-method", "factory_method/reportes.py", "factory-method/Exportadores.cs", "factory-method/Exportadores.hpp"),
    ("observer", "observer/eventos.py", "observer/MarcadorObservable.cs", "observer/MarcadorObservable.hpp"),
    ("strategy", "strategy/economia.py", "strategy/Economia.cs", "strategy/Economia.hpp"),
    ("strategy-puntuacion", "strategy_puntuacion/reglas.py", "strategy-puntuacion/Reglas.cs", "strategy-puntuacion/Reglas.hpp"),
    ("template-method", "template_method/plantilla.py", "template-method/Reportes.cs", "template-method/Reportes.hpp"),
]
LANGS = ("python", "csharp", "cpp")


def execute(command):
    result = subprocess.run([str(x) for x in command], capture_output=True,
                            encoding="utf-8", text=True, timeout=180, check=False)
    if result.returncode:
        raise RuntimeError(f"{command}: {result.stdout}\n{result.stderr}")
    return result.stdout.strip()


def expected(lang, path):
    return MANIFEST[f"{lang}/{TRACK}/{path}"].strip()


def main():
    env = os.environ
    env["DOTNET_CLI_TELEMETRY_OPTOUT"] = "1"
    env["DOTNET_NOLOGO"] = "1"
    with tempfile.TemporaryDirectory(prefix="ct-counter-strike-") as temp:
        tmp = Path(temp)
        cpp_root = PRACTICE / "cpp" / TRACK
        binary = tmp / ("counter-strike.exe" if os.name == "nt" else "counter-strike")
        execute(["g++", "-std=c++20", "-Wall", "-Wextra", "-Werror",
                 *sorted(cpp_root.rglob("*.cpp")), "-o", binary])
        cs_root = PRACTICE / "csharp" / TRACK
        project = tmp / "CounterStrike"
        execute(["dotnet", "new", "console", "-f", "net8.0", "-o", project])
        (project / "Program.cs").unlink()
        for file in cs_root.rglob("*.cs"):
            target = project / file.relative_to(cs_root)
            target.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(file, target)
        execute(["dotnet", "build", project, "--nologo", "-o", tmp / "out"])
        commands = {
            "python": [sys.executable, "-B", PRACTICE / "python" / TRACK / "main.py"],
            "csharp": ["dotnet", tmp / "out" / "CounterStrike.dll"],
            "cpp": [binary],
        }
        clients = {"python": "main.py", "csharp": "MainCounterStrike.cs", "cpp": "main.cpp"}
        # Comparar el contenido, no solo el código de salida del proceso.
        stories = {}
        for lang in LANGS:
            stories[lang] = execute(commands[lang])
            assert stories[lang] == expected(lang, clients[lang]), (lang, stories[lang])
        assert len(set(stories.values())) == 1, stories
        print("OK: las tres partidas completas tienen la misma narración")
        for slug, *paths in PATTERNS:
            results = {}
            for lang, path in zip(LANGS, paths):
                results[lang] = execute([*commands[lang], slug])
                assert results[lang] == expected(lang, path), (lang, slug, results[lang])
            assert len(set(results.values())) == 1, (slug, results)
            print("OK:", slug)
        print("OK: 42 ejecuciones verificadas (13 patrones + integrador por lenguaje).")


if __name__ == "__main__":
    main()
