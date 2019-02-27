package com.ashish.roomwithlivedata.activities;



import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.ashish.roomwithlivedata.R;
import com.ashish.roomwithlivedata.adaptors.PersonAdaptor;
import com.ashish.roomwithlivedata.database.AppDatabase;
import com.ashish.roomwithlivedata.database.AppExecutors;
import com.ashish.roomwithlivedata.model.Person;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    FloatingActionButton floatingActionButton;
    private RecyclerView mRecyclerView;
    private PersonAdaptor mAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.addFAB);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new PersonAdaptor(this);
        mRecyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Person> tasks = mAdapter.getTasks();
                        mDb.personDao().delete(tasks.get(position));

                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
        retrieveTasks();
    }

    private void retrieveTasks() {
        mDb.personDao().loadAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> people) {
                mAdapter.setTasks(people);
            }
        });
    }
}
