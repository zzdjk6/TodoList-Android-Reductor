package info.zzdjk6.todolist.reducers;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Reducer;

import java.util.UUID;

import info.zzdjk6.todolist.actions.AppActions;
import info.zzdjk6.todolist.models.TodoItem;

public class TodoReducer implements Reducer<ImmutableList<TodoItem>> {

    @Override
    public ImmutableList<TodoItem> reduce(ImmutableList<TodoItem> oldState, Action action) {
        switch (action.type) {
            case AppActions.ADD_ITEM:
                return addItem(oldState, action.getValue(0));
            case AppActions.REMOVE_ITEM:
                return removeItem(oldState, action.getValue(0));
            case AppActions.CHANGE_STATE:
                return changeState(oldState, action.getValue(0), action.getValue(1));
            case AppActions.EDIT_ITEM:
                return changeItemText(oldState, action.getValue(0), action.getValue(1));
            default:
                return oldState;
        }
    }

    // Pure function, easy to test
    private ImmutableList<TodoItem> addItem(ImmutableList<TodoItem> oldState, String text) {
        TodoItem newItem = new TodoItem(UUID.randomUUID(), text, false);

        ImmutableList.Builder<TodoItem> builder = ImmutableList.builder();
        return builder.addAll(oldState)
                .add(newItem)
                .build();
    }

    private ImmutableList<TodoItem> removeItem(ImmutableList<TodoItem> oldState, UUID itemId) {
        return FluentIterable
                .from(oldState)
                .filter(item -> !item.getId().equals(itemId))
                .toList();
    }

    private ImmutableList<TodoItem> changeItemText(ImmutableList<TodoItem> oldState, UUID itemId, String text) {
        return FluentIterable
                .from(oldState)
                .transform(input -> {
                    if (input.getId().equals(itemId)) {
                        return new TodoItem(itemId, text, input.isChecked());
                    }
                    return input;
                })
                .toList();
    }

    private ImmutableList<TodoItem> changeState(ImmutableList<TodoItem> oldState, UUID itemId, boolean isChecked) {
        return FluentIterable
                .from(oldState)
                .transform(input -> {
                    if (input.getId().equals(itemId)) {
                        return new TodoItem(itemId, input.getText(), isChecked);
                    }
                    return input;
                })
                .toList();
    }
}