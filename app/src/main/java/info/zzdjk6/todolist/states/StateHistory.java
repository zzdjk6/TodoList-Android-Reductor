package info.zzdjk6.todolist.states;

import java.util.ArrayList;
import java.util.List;

public class StateHistory<T> {

    private List<T> states = new ArrayList<>();

    private int cursor = 0;

    public void undo() {
        if (cursor == 0) {
            return;
        }

        cursor--;
    }

    public void redo() {
        if (cursor == states.size() - 1) {
            return;
        }

        cursor++;
    }

    public T getStateAtCursor() {
        return states.get(cursor);
    }

    public void pushState(T newState) {
        for (int i = cursor + 1; i < states.size(); i++) {
            states.remove(i);
        }

        states.add(newState);
        cursor = states.size() - 1;
    }
}
