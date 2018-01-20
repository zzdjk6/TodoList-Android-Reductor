package info.zzdjk6.todolist.models;

import java.io.Serializable;
import java.util.UUID;

// https://gist.github.com/Yarikx/7676bb77c6f0f03b7111227826b486ba#file-todoitem-java
public class TodoItem implements Serializable {
    private UUID id;
    private String text;
    private boolean isChecked;

    public TodoItem(UUID id, String text, boolean isChecked) {
        this.id = id;
        this.text = text;
        this.isChecked = isChecked;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "text='" + text + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}