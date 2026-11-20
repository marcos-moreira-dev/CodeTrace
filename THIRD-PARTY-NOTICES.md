# Avisos de componentes de terceros

CodeTrace es código original de **Marcos Moreira** bajo [MIT](LICENSE).
Esta relación distingue sus licencias de las correspondientes a
componentes redistribuidos. **No sustituye la auditoría del conjunto
transitivo de dependencias ni los textos de licencia íntegros exigibles
por cada componente**, especialmente antes de distribuir un binario.

| Componente | Uso | Licencia declarada por el proyecto |
|---|---|---|
| Eclipse Temurin / OpenJDK 21 | Runtime incluido mediante `jpackage` | GPL-2.0 with Classpath Exception y avisos adicionales incluidos por el distribuidor |
| OpenJFX / JavaFX 21 | UI, WebView y multimedia | GPL-2.0 with Classpath Exception |
| commonmark-java | Render de Markdown y tablas | BSD-2-Clause |
| emoji-java (vdurmont) | Resolución de nombres de emojis | MIT |
| Ikonli (Kordamp) | Sistema de iconos | Apache-2.0 |
| Devicons | Iconografía de lenguajes | MIT; identidades y marcas de lenguajes son de terceros |
| Feather Icons | Iconografía de acciones | MIT |
| IBM Plex | Tipografía opcional integrada | SIL Open Font License 1.1 |
| Twemoji (jdecked/twemoji) | Gráficos emoji SVG locales | Creative Commons Attribution 4.0 International |

Referencias oficiales:
- OpenJFX: https://github.com/openjdk/jfx y https://openjfx.io/
- OpenJDK: https://openjdk.org/legal/gplv2+ce.html
- commonmark-java: https://github.com/commonmark/commonmark-java
- emoji-java: https://github.com/vdurmont/emoji-java
- Ikonli: https://github.com/kordamp/ikonli
- Devicons: https://github.com/vorillaz/devicons
- Feather: https://github.com/feathericons/feather
- IBM Plex: https://github.com/IBM/plex
- Twemoji: https://github.com/jdecked/twemoji
- CC BY 4.0: https://creativecommons.org/licenses/by/4.0/

**Atribución Twemoji:** gráficos emoji de Twemoji, mantenidos por el
proyecto jdecked/twemoji, bajo CC BY 4.0. Se incluyen sin alteraciones
del arte original. El texto completo `LICENSE-GRAPHICS` se conserva en
`src/main/resources/emojis/twemoji/` y se copia al paquete Windows.

**Fuentes IBM Plex:** se empaquetan sin modificar, únicamente cuando
están disponibles los archivos verificados. Se conserva `fonts/OFL.txt`
en el repositorio y en el paquete Windows.

**FFmpeg:** por defecto NO se redistribuye. La aplicación puede usar
una instalación proporcionada por el usuario; cualquier opción
`-IncludeFfmpeg` exige examinar el build y sus licencias exactas antes
de distribuirlo.

**Pendiente de distribución general:** comprobar las licencias y los
`NOTICE` de los artefactos efectivamente empaquetados, incluidos
componentes transitivos y recursos, y acompañarlos con todos los
textos y obligaciones de distribución exigidos.
