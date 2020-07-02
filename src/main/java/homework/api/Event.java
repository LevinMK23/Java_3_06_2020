package homework.api;

public class Event {
    private Type type;
    private String message;

    public Event(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum Type {
        info("info"),
        error("error");

        Type(String type) {
        }
    }
}
