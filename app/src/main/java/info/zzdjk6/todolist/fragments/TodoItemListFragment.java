package info.zzdjk6.todolist.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.FluentIterable;

import java.util.List;

import info.zzdjk6.todolist.R;
import info.zzdjk6.todolist.TodoListApplication;
import info.zzdjk6.todolist.activities.EditTodoItemActivity;
import info.zzdjk6.todolist.models.TodoItem;
import info.zzdjk6.todolist.states.AppState;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TodoItemListFragment extends Fragment {
    TodoItemRecyclerViewAdapter mAdapter;

    public TodoItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TodoItemRecyclerViewAdapter();

        TodoListApplication app = (TodoListApplication) getActivity().getApplication();

        mAdapter.onTodoItemEditClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    Intent intent = new Intent(getActivity(), EditTodoItemActivity.class);
                    intent.putExtra(EditTodoItemActivity.ARG_TODO_ITEM, item);
                    startActivity(intent);
                });

        mAdapter.onTodoItemDeleteClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> app.removeTodoItem(item.getId()));

        mAdapter.onTodoItemToggleCheckClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    boolean checked = item.isChecked();
                    app.changeTodoItemState(item.getId(), !checked);
                });

        app.getStore().subscribe(this::updateItems);
        updateItems(app.getStore().getState());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todoitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    private void updateItems(AppState state) {
        List<TodoItem> items = FluentIterable
                .from(state.todoItems)
                .filter(item -> {
                    boolean flag = false;
                    switch (state.filter) {
                        case ALL:
                            flag = true;
                            break;
                        case CHECKED:
                            flag = item.isChecked();
                            break;
                        case UNCHECKED:
                            flag = !item.isChecked();
                            break;
                    }
                    return flag;
                })
                .toList();
        mAdapter.setItems(items);
    }
}
