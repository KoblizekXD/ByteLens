package lol.koblizek.bytelens.util;

import lol.koblizek.bytelens.ByteLens;

public interface IResourceful {
    default String getValue(String key) {
        return ByteLens.getInstance().getResourceManager().get(key);
    }
}
