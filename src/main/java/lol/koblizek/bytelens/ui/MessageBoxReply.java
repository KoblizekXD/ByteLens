package lol.koblizek.bytelens.ui;

public enum MessageBoxReply {
    OK(0),
    CANCEL(2),
    YES(0),
    NO(1);

    private final int i;

    MessageBoxReply(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public static MessageBoxReply valueOf(int i) {
        for (MessageBoxReply value : values()) {
            if (value.getI() == i) {
                return value;
            }
        }
        return null;
    }
}
