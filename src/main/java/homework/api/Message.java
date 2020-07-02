package homework.api;

public class Message {
    private String path;
    private String body;

    public Message(String path, String body) {
        this.path = path;
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
