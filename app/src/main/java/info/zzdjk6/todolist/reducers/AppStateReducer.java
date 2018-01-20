package info.zzdjk6.todolist.reducers;

import com.google.common.collect.ImmutableList;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Reducer;

import info.zzdjk6.todolist.actions.AppActions;
import info.zzdjk6.todolist.models.TodoFilter;
import info.zzdjk6.todolist.models.TodoItem;
import info.zzdjk6.todolist.states.AppState;
import info.zzdjk6.todolist.states.StateHistory;

public class AppStateReducer implements Reducer<AppState> {

    private StateHistory<AppState> stateHistory;

    // Use Guava immutable list to prevent unwanted modifications and be thread-safe
    private Reducer<ImmutableList<TodoItem>> itemsReducer = new TodoReducer();

    private Reducer<TodoFilter> filterReducer = new FilterReducer();

    public AppStateReducer(StateHistory<AppState> stateHistory) {
        this.stateHistory = stateHistory;
    }

    @Override
    public AppState reduce(AppState oldState, Action action) {
        if (action.type.equals(AppActions.SET_STATE)) {
            return (AppState) action.getValue(0);
        }

        ImmutableList<TodoItem> items = itemsReducer.reduce(oldState.todoItems, action);
        TodoFilter filter = filterReducer.reduce(oldState.filter, action);
        AppState newState = new AppState(items, filter);
        stateHistory.pushState(newState);
        return newState;
    }
}