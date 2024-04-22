package lol.koblizek.bytelens.init;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.resource.ResourceManager;
import lol.koblizek.bytelens.ui.forms.MainMenuForm;
import lol.koblizek.bytelens.util.State;

public class Bootstrap {

    private static boolean initialized = false;

    public static State init(ByteLens byteLens) {
        if (initialized) {
            return State.failing("Bootstrap can not be initialized multiple times");
        }

        byteLens.setResourceManager(ResourceManager.init());
        ResourceManager resourceManager = byteLens.getResourceManager();
        resourceManager.selectTranslationSource(resourceManager.getResource("/langs/en_us.properties"));

        FlatMacDarkLaf.setup();

        byteLens.currentMainForm = new MainMenuForm();

        initialized = true;
        return State.ok();
    }
}
