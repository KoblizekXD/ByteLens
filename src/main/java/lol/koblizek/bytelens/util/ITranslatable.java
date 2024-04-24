package lol.koblizek.bytelens.util;

import lol.koblizek.bytelens.ByteLens;

/**
 * Interface for classes that need to access translation pairs.
 */
public interface ITranslatable {
    /**
     * Returns the value of the translation pair with the given key.
     * @param key Key of the translation pair.
     * @return Value of the translation pair.
     */
    default String getValue(String key) {
        return ByteLens.getInstance().getResourceManager().get(key);
    }
}
