package lol.koblizek.bytelens;

import lol.koblizek.bytelens.init.Bootstrap;
import lol.koblizek.bytelens.resource.ResourceManager;
import lol.koblizek.bytelens.ui.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ByteLens {

    private static ByteLens instance;

    public static ByteLens getInstance() {
        return instance;
    }

    private final Logger logger;
    private ResourceManager resourceManager;

    public Form currentMainForm;

    private ByteLens(String... args) {
        logger = LoggerFactory.getLogger(ByteLens.class);
        logger.debug("Starting ByteLens");
        var result = Bootstrap.init(this);
        if (result.failed()) {
            logger.error("Failed to start ByteLens: {}", result.getMessage());
            System.exit(1);
        }
        currentMainForm.showForm();
    }

    public static void main(String[] args) {
        if (instance == null) {
            instance = new ByteLens(args);
        } else {
            throw new IllegalStateException("Program already running");
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        if (this.resourceManager != null) {
            throw new IllegalStateException("Resource manager already set");
        }
        this.resourceManager = resourceManager;
    }
}
