package lol.koblizek.bytelens.resource;

import java.util.Properties;

public final class ResourceManager {

    private static ResourceManager instance;

    public static ResourceManager getInstance() {
        return instance;
    }

    private Properties translationKeys;

    private ResourceManager() {
        instance = this;
    }

    public static ResourceManager init() {
        if (instance == null) {
            return (instance = new ResourceManager());
        } else {
            throw new IllegalStateException("ResourceManager is already initialized");
        }
    }

    public Resource<?> getResource(String name) {
        return new Resource<>(this.getClass().getResource(name), null);
    }

    public <T> Resource<T> getResource(String name, Class<T> type) {
        return new Resource<>(this.getClass().getResource("/" + name), type);
    }

    public void selectTranslationSource(Resource<?> resource) {
        translationKeys = resource.asProperties();
    }

    public String get(String key) {
        return translationKeys.getProperty(key);
    }
}
