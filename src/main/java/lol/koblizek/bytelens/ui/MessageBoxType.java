package lol.koblizek.bytelens.ui;

public enum MessageBoxType {
    INFO(1),
    WARNING(2),
    ERROR(0),
    QUESTION(3);

    private final int i;

    MessageBoxType(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
