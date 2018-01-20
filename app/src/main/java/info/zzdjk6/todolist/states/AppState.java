package info.zzdjk6.todolist.states;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;

import info.zzdjk6.todolist.models.TodoFilter;
import info.zzdjk6.todolist.models.TodoItem;

public class AppState implements Serializable {
    public final ImmutableList<TodoItem> todoItems;
    public final TodoFilter filter;

    public AppState(ImmutableList<TodoItem> todoItems,
                    TodoFilter filter) {
        this.todoItems = todoItems;
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "AppState{" +
                "todoItems=" + todoItems +
                ", filter=" + filter +
                '}';
    }
}