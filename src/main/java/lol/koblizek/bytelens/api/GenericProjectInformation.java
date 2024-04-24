package lol.koblizek.bytelens.api;

import java.nio.file.Path;

public record GenericProjectInformation(String name, Path projectDir) {
    public String getGenericName() {
        return name.toLowerCase().replace(' ', '_');
    }
}
