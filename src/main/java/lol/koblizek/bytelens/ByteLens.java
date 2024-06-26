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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        logger.info("Starting ByteLens");
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

    public State addProject(Path projectFile) {
        try (var reader = new FileReader(getLocalApplicationData().resolve("projects.json").toFile())) {
            var projects = gson.fromJson(reader, String[].class);
            if (Arrays.asList(projects).contains(projectFile.toString())) {
                return State.failing("Project file already exists at location" + projectFile);
            }
            var newProjects = new String[projects.length + 1];
            System.arraycopy(projects, 0, newProjects, 0, projects.length);
            newProjects[projects.length] = projectFile.toString();
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

    public void deleteProject(Path projectFile) {
        try (var reader = new FileReader(getLocalApplicationData().resolve("projects.json").toFile())) {
            logger.warn("Removing project at {} from database...", projectFile);
            List<String> projects = gson.fromJson(reader, List.class);
            var newProjects = new String[projects.size() - 1];
            projects.remove(projectFile.toString());
            projects.toArray(newProjects);
            try (var writer = new FileWriter(getLocalApplicationData().resolve("projects.json").toFile())) {
                gson.toJson(newProjects, writer);
            }
            logger.warn("Deleting directory {}", projectFile.getParent());
            Files.walk(projectFile.getParent())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
