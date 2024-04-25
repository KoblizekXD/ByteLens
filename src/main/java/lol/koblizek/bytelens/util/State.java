package lol.koblizek.bytelens.util;

import java.util.function.Consumer;

public class State {
    private String message;
    private Status status;

    public State(Status status, String message) {
        this.message = message;
        this.status = status;
    }

    public boolean failed() {
        return status != Status.OK;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        OK, WARNING, ERROR
    }

    public static State failing(String message) {
        return new State(Status.ERROR, message);
    }

    public static State ok() {
        return new State(Status.OK, null);
    }

    public State then(Consumer<State> consumer) {
        consumer.accept(this);
        return this;
    }

    public State ifFailed(Consumer<State> consumer) {
        if (failed()) {
            consumer.accept(this);
        }
        return this;
    }

    public State ifOk(Consumer<State> consumer) {
        if (!failed()) {
            consumer.accept(this);
        }
        return this;
    }
}
