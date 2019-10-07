const chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@!?;.,";

// On renvoie un entier aléatoire entre une valeur min (incluse)
// et une valeur max (incluse).
// Attention : si on utilisait Math.round(), on aurait une distribution
// non uniforme !
function getRandomIntInclusive(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min +1)) + min;
}

/**
 * Generate a random password
 * @param {number} n : Number of characters do you want to generate
 */
function generate(n) {
    if (n<=0) return "";
    else {
        return chars[getRandomIntInclusive(0,chars.length-1)] + generate(n-1);
    }
}