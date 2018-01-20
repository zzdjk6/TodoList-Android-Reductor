package info.zzdjk6.todolist.reducers;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Reducer;

import info.zzdjk6.todolist.actions.AppActions;
import info.zzdjk6.todolist.models.TodoFilter;

public class FilterReducer implements Reducer<TodoFilter> {

    @Override
    public TodoFilter reduce(TodoFilter oldState, Action action) {
        switch (action.type) {
            case AppActions.SET_FILTER:
                TodoFilter filter = action.getValue(0);
                if (filter == oldState) {
                    return oldState;
                }
                return filter;
            default:
                return oldState;
        }
    }
}
