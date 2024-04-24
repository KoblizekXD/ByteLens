package lol.koblizek.bytelens.api;

public class Project {

    private final ProjectType type;
    private final GenericProjectInformation info;

    public Project(ProjectType type, GenericProjectInformation info) {
        this.type = type;
        this.info = info;
    }
}
