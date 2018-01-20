package info.zzdjk6.todolist.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.zzdjk6.todolist.R;
import info.zzdjk6.todolist.TodoListApplication;
import info.zzdjk6.todolist.models.TodoItem;

public class EditTodoItemActivity extends AppCompatActivity {

    public static final String ARG_TODO_ITEM = "ARG_TODO_ITEM";

    private TodoItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_item);

        mItem = (TodoItem) getIntent().getSerializableExtra(ARG_TODO_ITEM);

        EditText editText = findViewById(R.id.editText);

        if (mItem != null) {
            editText.setText(mItem.getText());
        }

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(view -> {
            String text = editText.getText().toString().trim();
            if (text.length() == 0) {
                Toast.makeText(this, "Error: empty text", Toast.LENGTH_SHORT).show();
                return;
            }

            if (text.length() > 10) {
                Toast.makeText(this, "Error: too long text", Toast.LENGTH_SHORT).show();
                return;
            }

            TodoListApplication app = (TodoListApplication) getApplication();
            if (mItem != null) {
                app.changeTodoItemText(mItem.getId(), text);
            } else {
                app.addTodoItem(text);
            }
            finish();
        });
    }
}
