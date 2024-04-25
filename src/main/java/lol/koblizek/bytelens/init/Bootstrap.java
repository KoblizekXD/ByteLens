package lol.koblizek.bytelens.init;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.resource.ResourceManager;
import lol.koblizek.bytelens.ui.forms.MainMenuForm;
import lol.koblizek.bytelens.util.State;
import lol.koblizek.bytelens.util.SystemExceptionHandler;

import java.io.File;
import java.nio.file.Files;

public class Bootstrap {

    private static boolean initialized = false;

    public static State init(ByteLens byteLens) {
        if (initialized) {
            return State.failing("Bootstrap can not be initialized multiple times");
        }

        try {
            File localDir = byteLens.getLocalApplicationData().toFile();
            if (!localDir.exists())
                localDir.mkdirs();
            File projects = localDir.toPath().resolve("projects.json").toFile();
            if (!projects.exists()) {
                projects.createNewFile();
                Files.writeString(projects.toPath(), "[]");
            }
        } catch (Exception e) {
            byteLens.getLogger()
                    .warn("Failed to create local directory for app data, any application data won't be saved");
        }

        Thread.setDefaultUncaughtExceptionHandler(new SystemExceptionHandler());

        byteLens.setResourceManager(ResourceManager.init());
        ResourceManager resourceManager = byteLens.getResourceManager();
        resourceManager.selectTranslationSource(resourceManager.getResource("/langs/en_us.properties"));

        FlatMacDarkLaf.setup();

        byteLens.currentMainForm = new MainMenuForm();

        initialized = true;
        return State.ok();
    }
}
