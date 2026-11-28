// Tema:
// OmitThisParameter obtiene un tipo de función sin su parámetro this explícito.

function capitalize(this: String) {
    return this[0].toUpperCase() + this.substring(1).toLowerCase();
}

type CapitalizeType = OmitThisParameter<typeof capitalize>; // () => string
