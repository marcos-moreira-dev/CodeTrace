// Tema:
// Un const enum permite que las referencias a sus miembros se sustituyan por valores
// constantes según la configuración de compilación.

const enum Language {
    English = 'EN',
    Spanish = 'ES',
}
console.log(Language.English);
