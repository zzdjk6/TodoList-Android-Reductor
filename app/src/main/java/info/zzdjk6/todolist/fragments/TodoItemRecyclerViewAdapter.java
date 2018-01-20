package info.zzdjk6.todolist.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.zzdjk6.todolist.R;
import info.zzdjk6.todolist.models.TodoItem;
import io.reactivex.subjects.PublishSubject;

public class TodoItemRecyclerViewAdapter extends RecyclerView.Adapter<TodoItemRecyclerViewAdapter.ViewHolder> {

    private List<TodoItem> mItems = new ArrayList<>();
    PublishSubject<TodoItem> onTodoItemEditClicked = PublishSubject.create();
    PublishSubject<TodoItem> onTodoItemDeleteClicked = PublishSubject.create();
    PublishSubject<TodoItem> onTodoItemToggleCheckClicked = PublishSubject.create();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_todoitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TodoItem item = mItems.get(position);
        holder.mItem = item;
        holder.mContentTextView.setText(item.getText());
        holder.mEditButton.setOnClickListener(view -> onTodoItemEditClicked.onNext(item));
        holder.mDeleteButton.setOnClickListener(view -> onTodoItemDeleteClicked.onNext(item));
        holder.mCheckBox.setChecked(item.isChecked());
        holder.mCheckBox.setOnClickListener(view -> onTodoItemToggleCheckClicked.onNext(item));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    void setItems(List<TodoItem> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentTextView;
        final ImageButton mEditButton;
        final ImageButton mDeleteButton;
        final CheckBox mCheckBox;

        TodoItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mContentTextView = view.findViewById(R.id.content);
            mEditButton = view.findViewById(R.id.editButton);
            mDeleteButton = view.findViewById(R.id.deleteButton);
            mCheckBox = view.findViewById(R.id.checkBox);
        }
    }
}
