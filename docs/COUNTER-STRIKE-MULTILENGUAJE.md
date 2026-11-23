# Counter-Strike — colección comparativa Java, C#, Python y C++

La sección 05 contiene los 13 ejemplos originales Java y una fachada de partida. Las tres pistas nuevas preservan la temática y los colaboradores, no una traducción textual: C# usa eventos, delegados y propiedades; Python usa dataclasses, protocolos, funciones y paquetes; C++ usa referencias, RAII, unique_ptr, cabeceras e implementaciones.

Los 13 ejemplos de cada nueva pista pueden ejecutarse individualmente pasando el nombre de la carpeta (abstract-factory, adapter, builder, chain-of-responsibility, command, composite, decorator, factory, factory-method, observer, strategy, strategy-puntuacion, template-method) como argumento opcional. Sin argumento, la fachada inicia tres rondas automáticamente. No hay lectura del teclado.

El dominio reproduce la historia de Java: dos equipos, Astra, Nilo, Rook, Vega, dos NPC rescatables (Elena y Omar), créditos, vida, armadura, inventario, marcador, reloj e historial. Los valores de compra y estado son ilustrativos. La secuencia rescate, tiempo agotado, rescate reproduce el desenlace de Java con semilla 42; se fija explícitamente para no depender de los RNG de cada lenguaje. No implementa el motor del videojuego. Los archivos de las tres pistas incluyen comentarios sobre referencias, estados de los NPC, cambios de créditos y colaboración entre patrones, además de instrucciones y salidas esperadas.

## Ejecución local desde la raíz del repositorio

Python:
```powershell
python -B .\src\main\resources\practice\python\05-patrones-diseno-counter-strike\main.py
```

C#:
```powershell
$d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
dotnet new console -f net8.0 -o $d
Remove-Item "$d\Program.cs"
Get-ChildItem .\src\main\resources\practice\csharp\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
dotnet run --project $d
```

C++:
```powershell
$fuentes=(Get-ChildItem .\src\main\resources\practice\cpp\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
& "$env:TEMP\ct-counter-strike.exe"
```

Ejemplo individual: añadir `observer` al comando de Python o C++, o `-- observer` al de dotnet run.

## Verificación conjunta

`python scripts/verificar-counter-strike.py` compila las fuentes en temporales y compara exactamente las 42 ejecuciones con el manifiesto. Requiere Python 3.12, SDK .NET 8 y g++ con C++20. La salida de las tres narraciones es idéntica; Java conserva su narración más extensa, sin cambios. Las colecciones generales y los demás idiomas permanecen intactos.

**Alcance:** 72 fuentes nuevas (33 Python, 18 C#, 21 C++), incluidos módulos de paquetes, 13 demostraciones por idioma y MVC/Facade. Catálogo v14: 990 referencias, 14 lenguajes. Los ejemplos son didácticos e independientes, no una implementación oficial de Counter-Strike.
