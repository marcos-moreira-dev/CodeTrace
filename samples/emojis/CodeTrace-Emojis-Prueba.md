# 🧪 CodeTrace — Banco de prueba de emojis

**Objetivo:** comparar la vista **Fuente** y **Renderizado**.

> ⚠️ Si un emoji compuesto aparece separado, anota cuál y en qué vista.

## 😀 Caras y emociones

😀 😃 😄 😁 😆 😅 😂 🤣 😊 🙂 🙃 😉 😍 🥰 😎 🤔 😭 🫠 🥹

## 👋 Manos y tonos de piel

👍 👋 🤝 🙌 👏 🫶 🤘 👌 👋🏻 👋🏽 👋🏿 👍🏿 🫱🏽‍🫲🏻

## 👨‍👩‍👧‍👦 Familias, género y profesiones

👨‍👩‍👧‍👦 👩‍👩‍👦 👨‍👨‍👧 👩🏽‍💻 🧑🏾‍🔬 👩🏻‍🚀 🧑‍🎓 🧑‍🦽

## 🇪🇨 Banderas y viajes

🇪🇨 🇯🇵 🇺🇸 🇧🇷 🇬🇧 🌋 🏔️ 🗺️ 🚗 🚲 🚀 ✈️

## 💻 Computación y ciencia

💻 🖥️ 🖱️ ⌨️ 📱 📡 🔌 💾 🤖 ⚙️ 🔬 🧪 🧬 📊 📈 📐 🧠

## ✅ Símbolos

✅ ✔️ ☑️ ❌ ⚠️ ℹ️ ♻️ ❤️ 💙 🩷 ☀️ 🌈 🎭 🎬

## 🔣 Variantes con Unicode

✈ / ✈️, ❤ / ❤️, ☀ / ☀️, ☺ / ☺️, 0️⃣ / 1️⃣ / 2️⃣, #️⃣ / *️⃣.

## 📋 Tabla de comprobación

| Caso | Emoji | Comportamiento esperado |
|---|---|---|
| Simple | 😀 😎 | Visible |
| Familia (ZWJ) | 👨‍👩‍👧‍👦 | Secuencia unida |
| Bandera | 🇪🇨 🇯🇵 | Banderas completas |
| Tono | 👩🏽‍💻 | Profesión con tono |
| Keycap | 1️⃣ #️⃣ | Carácter compuesto |

## 💻 Código con emojis

```javascript
const estado = { icono: "✅", mensaje: "Listo 🥳" };
console.log(`${estado.icono} ${estado.mensaje}`);
```

```java
String mensaje = "Hecho ✅ — Ecuador 🇪🇨";
System.out.println(mensaje);
```

```python
print("Hola 👩🏽‍💻, terminado ✅")
```

## Alias compatibles

Texto normal: :rocket: :smile: :white_check_mark: :heart: :tada:

Dentro de código no se expande el alias: `:rocket:`.

- **Negrita:** ✅ Validado.
- *Cursiva:* 📝 En estudio.
- ~~Tachado:~~ ❌ Descartado.

**Fin 🏁**
