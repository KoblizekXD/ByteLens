package lol.koblizek.bytelens.api;

import java.io.File;
import java.nio.file.Path;

public class Project {

    private final ProjectType type;
    private final GenericProjectInformation info;

    public Project(ProjectType type, GenericProjectInformation info) {
        this.type = type;
        this.info = info;
    }

    public ProjectType getType() {
        return type;
    }

    public GenericProjectInformation getInfo() {
        return info;
    }

    public File getProjectFile() {
        return info.projectDir().resolve(info.getGenericName() + ".byteproj").toFile();
    }

    public Path getProjectDirectory() {
        return info.projectDir();
    }
}
