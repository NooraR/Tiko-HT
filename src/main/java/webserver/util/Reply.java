package webserver.util;

import com.google.gson.annotations.Expose;

public class Reply {
    @Expose
    public boolean success;

    @Expose
    public String message;

    @Expose
    public Object data;

    public Reply(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
