package webserver.util;

public class Reply {
    public boolean success;
    public String message;
    public Object data;

    public Reply(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
