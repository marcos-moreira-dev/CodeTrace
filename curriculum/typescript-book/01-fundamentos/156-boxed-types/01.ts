// Tema:
// Los tipos envoltorio y los tipos primitivos no son equivalentes. Generalmente se prefieren
// los tipos primitivos en anotaciones.

const originalNormalize = String.prototype.normalize;
String.prototype.normalize = function () {
    console.log(this, typeof this);
    return originalNormalize.call(this);
};
console.log('\u0041'.normalize());
