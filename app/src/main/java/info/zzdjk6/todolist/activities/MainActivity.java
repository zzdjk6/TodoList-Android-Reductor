package info.zzdjk6.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import info.zzdjk6.todolist.R;
import info.zzdjk6.todolist.TodoListApplication;
import info.zzdjk6.todolist.api.WeatherApi;
import info.zzdjk6.todolist.fragments.TodoItemListFragment;
import info.zzdjk6.todolist.models.TodoFilter;
import info.zzdjk6.todolist.states.AppState;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private boolean selectTabByStateChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabLayout();
        initListFragment();
        loadWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TodoListApplication app = (TodoListApplication) getApplication();
        switch (item.getItemId()) {
            case R.id.undo:
                app.undo();
                return true;
            case R.id.redo:
                app.redo();
                return true;
            case R.id.add:
                Intent intent = new Intent(this, EditTodoItemActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                app.saveAndExit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initTabLayout() {
        TodoListApplication app = (TodoListApplication) getApplication();
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (selectTabByStateChange) {
                    // the tab is selected because of state change,
                    // do not modify the state again
                    selectTabByStateChange = false;
                    return;
                }
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        app.setFilter(TodoFilter.ALL);
                        break;
                    case 1:
                        app.setFilter(TodoFilter.UNCHECKED);
                        break;
                    case 2:
                        app.setFilter(TodoFilter.CHECKED);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        app.getStore().subscribe(this::updateTabLayout);
        updateTabLayout(app.getStore().getState());
    }

    private void updateTabLayout(AppState state) {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        int index = 0;
        switch (state.filter) {
            case ALL:
                index = 0;
                break;
            case UNCHECKED:
                index = 1;
                break;
            case CHECKED:
                index = 2;
                break;
        }
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if (tab != null && !tab.isSelected()) {
            selectTabByStateChange = true;
            tab.select();
        }
    }

    private void initListFragment() {
        TodoItemListFragment mTodoItemListFragment = new TodoItemListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragmentContainer, mTodoItemListFragment)
                .commit();
    }

    private void loadWeather() {
        WeatherApi
                .fetchWeather()
                .retry(3)
                .map(weather -> weather.location + ": " + weather.temperature + ", " + weather.condition)
                .onErrorReturn(throwable -> "Failed to load weather")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    TextView textView = findViewById(R.id.weatherText);
                    textView.setText(text);
                });
    }
}
