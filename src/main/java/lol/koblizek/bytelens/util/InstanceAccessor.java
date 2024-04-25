package lol.koblizek.bytelens.util;

import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.resource.Resource;
import org.slf4j.Logger;

public interface InstanceAccessor {
    default ByteLens instance() {
        return ByteLens.getInstance();
    }

    default String translate(String key) {
        return instance().getResourceManager().get(key);
    }

    default Resource<?> resource(String key) {
        return instance().getResourceManager().getResource(key);
    }

    default Logger logger() {
        return instance().getLogger();
    }
}