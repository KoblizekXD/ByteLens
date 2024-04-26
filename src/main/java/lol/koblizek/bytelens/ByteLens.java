package lol.koblizek.bytelens;

import com.google.gson.Gson;
import lol.koblizek.bytelens.init.Bootstrap;
import lol.koblizek.bytelens.resource.ResourceManager;
import lol.koblizek.bytelens.ui.Form;
import lol.koblizek.bytelens.util.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public final class ByteLens {

    private static ByteLens instance;

    public static ByteLens getInstance() {
        return instance;
    }

    private final Logger logger;
    private ResourceManager resourceManager;
    private final File appDataDir;
    private final Gson gson;

    public Form currentMainForm;

    private ByteLens(String... args) {
        logger = LoggerFactory.getLogger(ByteLens.class);
        logger.debug("Starting ByteLens");
        appDataDir = new File("./.bl/");
        gson = new Gson();
        var result = Bootstrap.init(this);
        if (result.failed()) {
            logger.error("Failed to start ByteLens: {}", result.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        if (instance == null) {
            instance = new ByteLens(args);
            instance.currentMainForm.showForm();
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

    public Path getLocalApplicationData() {
        return appDataDir.toPath();
    }

    public Gson getGson() {
        return gson;
    }

    public Path[] getProjects() {
        try (var reader = new FileReader(getLocalApplicationData().resolve("projects.json").toFile())) {
            return Arrays.stream(gson.fromJson(reader, String[].class)).map(Path::of).toArray(Path[]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public State addProject(Path configFile) {
        try (var reader = new FileReader(getLocalApplicationData().resolve("projects.json").toFile())) {
            var projects = gson.fromJson(reader, String[].class);
            if (Arrays.asList(projects).contains(configFile.toString())) {
                return State.failing("Project file already exists at location" + configFile);
            }
            var newProjects = new String[projects.length + 1];
            System.arraycopy(projects, 0, newProjects, 0, projects.length);
            newProjects[projects.length] = configFile.toString();
            try (var writer = new FileWriter(getLocalApplicationData().resolve("projects.json").toFile())) {
                gson.toJson(newProjects, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return State.ok();
    }

    public boolean projectRegistered(Path projectFile) {
        for (Path project : getProjects()) {
            if (project.equals(projectFile)) {
                return true;
            }
        }
        return false;
    }
}
