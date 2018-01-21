package info.zzdjk6.todolist.reducers;

import com.google.common.collect.ImmutableList;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Reducer;

import info.zzdjk6.todolist.actions.AppActions;
import info.zzdjk6.todolist.models.TodoFilter;
import info.zzdjk6.todolist.models.TodoItem;
import info.zzdjk6.todolist.states.AppState;

public class AppStateReducer implements Reducer<AppState> {

    // Use Guava immutable list to prevent unwanted modifications and be thread-safe
    private Reducer<ImmutableList<TodoItem>> itemsReducer = new TodoReducer();

    private Reducer<TodoFilter> filterReducer = new FilterReducer();

    @Override
    public AppState reduce(AppState oldState, Action action) {
        // reduce action that set state directly
        if (action.type.equals(AppActions.SET_STATE)) {
            return (AppState) action.getValue(0);
        }

        // reduce actions that change items
        ImmutableList<TodoItem> items = itemsReducer.reduce(oldState.todoItems, action);

        // reduce actions that change filter
        TodoFilter filter = filterReducer.reduce(oldState.filter, action);

        return new AppState(items, filter);
    }
}