package info.zzdjk6.todolist;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.common.collect.ImmutableList;
import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Store;

import java.util.UUID;

import info.zzdjk6.todolist.actions.AppActions;
import info.zzdjk6.todolist.helpers.ObjectHelper;
import info.zzdjk6.todolist.models.TodoFilter;
import info.zzdjk6.todolist.reducers.AppStateReducer;
import info.zzdjk6.todolist.states.AppState;
import info.zzdjk6.todolist.states.StateHistory;


public class TodoListApplication extends Application {

    Store<AppState> mStore;
    StateHistory<AppState> mStateHistory;

    @Override
    public void onCreate() {
        super.onCreate();

        mStateHistory = new StateHistory<>();
        mStore = Store.create(
                new AppStateReducer(mStateHistory),
                new AppState(ImmutableList.of(), TodoFilter.ALL)
        );
        mStore.subscribe(state -> Log.d(TodoListApplication.class.toString(), state.toString()));

        loadState();
    }

    private void loadState() {
        try {
            SharedPreferences preferences = getSharedPreferences(TodoListApplication.class.toString(), Context.MODE_PRIVATE);
            String serializedObject = preferences.getString("STATE", "");
            AppState state = (AppState) ObjectHelper.stringToObject(serializedObject);

            if (state != null) {
                Action action = Action.create(AppActions.SET_STATE, state);
                mStore.dispatch(action);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveAndExit() {
        try {
            String serializedObject = ObjectHelper.objectToString(mStore.getState());
            SharedPreferences preferences = getSharedPreferences(TodoListApplication.class.toString(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("STATE", serializedObject);
            editor.commit();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.exit(0);
    }

    public void undo() {
        mStateHistory.undo();
        Action action = Action.create(AppActions.SET_STATE, mStateHistory.getStateAtCursor());
        mStore.dispatch(action);
    }

    public void redo() {
        mStateHistory.redo();
        Action action = Action.create(AppActions.SET_STATE, mStateHistory.getStateAtCursor());
        mStore.dispatch(action);
    }

    public void addTodoItem(String text) {
        Action action = Action.create(AppActions.ADD_ITEM, text);
        mStore.dispatch(action);
    }

    public void changeTodoItemText(UUID itemId, String text) {
        Action action = Action.create(AppActions.EDIT_ITEM, itemId, text);
        mStore.dispatch(action);
    }

    public void changeTodoItemState(UUID itemId, boolean isChecked) {
        Action action = Action.create(AppActions.CHANGE_STATE, itemId, isChecked);
        mStore.dispatch(action);
    }

    public void removeTodoItem(UUID itemId) {
        Action action = Action.create(AppActions.REMOVE_ITEM, itemId);
        mStore.dispatch(action);
    }

    public void setFilter(TodoFilter filter) {
        Action action = Action.create(AppActions.SET_FILTER, filter);
        getStore().dispatch(action);
    }

    public Store<AppState> getStore() {
        return mStore;
    }
}
