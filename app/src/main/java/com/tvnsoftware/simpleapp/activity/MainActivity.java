package com.tvnsoftware.simpleapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.tvnsoftware.simpleapp.R;
import com.tvnsoftware.simpleapp.adapter.TaskAdapter;
import com.tvnsoftware.simpleapp.contants.AppContants;
import com.tvnsoftware.simpleapp.model.Task;
import com.tvnsoftware.simpleapp.utils.DatabaseHelper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRcToDoTasks;
    private LinearLayoutManager mLayoutManager;
    private TaskAdapter mTasksAdapter;
    private EditText mAddText;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRcToDoTasks = (RecyclerView) findViewById(R.id.rc_todo_tasks);
        mLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        mTasksAdapter = new TaskAdapter(this, new TaskAdapter.TaskListener() {
            @Override
            public void onClickTask(Task task) {
                transferToView(task);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mTasksAdapter.getContext(),
                mLayoutManager.getOrientation());
        mRcToDoTasks.addItemDecoration(dividerItemDecoration);
        mRcToDoTasks.setLayoutManager(mLayoutManager);
        mRcToDoTasks.setItemAnimator(new DefaultItemAnimator());
        mRcToDoTasks.setAdapter(mTasksAdapter);
        findViewById(R.id.btn_add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferToCreate();
            }


        });
        mDatabaseHelper = new DatabaseHelper(this);
        getData();
    }

    private void getData() {
        try {
            List<Task> taskList = mDatabaseHelper.getAll(Task.class);
            mTasksAdapter.addNewData(taskList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTask() {
        Task task = new Task();
        task.setName(mAddText.getText().toString().trim());
        try {
            mDatabaseHelper.createOrUpdate(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void transferToCreate() {
        Intent intent = new Intent(getBaseContext(), CreateAndEditActivity.class);
        startActivityForResult(intent, AppContants.RESULT_1);
    }

    private void transferToView(Task task) {
        Intent intent = new Intent(getBaseContext(), ViewTaskActivity.class);
        intent.putExtra(AppContants.OBJECT_TASK, (Serializable) task);
        startActivityForResult(intent, AppContants.RESULT_2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == AppContants.RESULT_1 || requestCode == AppContants.RESULT_2) {
            if (resultCode == RESULT_OK) {
                getData();
            }
        }
    }
}