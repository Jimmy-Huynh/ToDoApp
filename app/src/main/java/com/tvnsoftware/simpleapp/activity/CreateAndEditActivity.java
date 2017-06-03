package com.tvnsoftware.simpleapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tvnsoftware.simpleapp.R;
import com.tvnsoftware.simpleapp.contants.AppContants;
import com.tvnsoftware.simpleapp.model.Task;
import com.tvnsoftware.simpleapp.utils.CustomSpinerAdapter;
import com.tvnsoftware.simpleapp.utils.DatabaseHelper;
import com.tvnsoftware.simpleapp.utils.DatePickerFragment;

import java.sql.SQLException;
import java.util.Arrays;

public class CreateAndEditActivity extends AppCompatActivity {
    private EditText mEdtName, mEdtDuaDate, mEdtNote;
    private Spinner mSpnPriority, mSpnStatus;
    private DatabaseHelper mDatabaseHelper;
    private Task mTask;
    private String[] priority = null;
    private String[] status = null;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_edit);
        mDatabaseHelper = new DatabaseHelper(this);
        mEdtName = (EditText) findViewById(R.id.edt_task_name);
        mEdtDuaDate = (EditText) findViewById(R.id.edt_due_date);
        mEdtNote = (EditText) findViewById(R.id.edt_task_note);
        mSpnPriority = (Spinner) findViewById(R.id.edt_task_priority);
        mSpnStatus = (Spinner) findViewById(R.id.edt_task_status);
        mTitle = (TextView) findViewById(R.id.tv_title);
        loadSpiner();
        mTask = (Task) getIntent().getSerializableExtra(AppContants.OBJECT_TASK);
        if (null != mTask) {
            setValue();
            findViewById(R.id.btn_add_new).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTask();
                    Intent intent = new Intent();
                    intent.putExtra(AppContants.OBJECT_TASK_BACK, mTask);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            mTitle.setText("Edit your tak");

        } else {
            findViewById(R.id.btn_add_new).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTask();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            mTitle.setText("Create new task");
        }
        findViewById(R.id.btn_close_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEdtDuaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment(mEdtDuaDate);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void addTask() {
        Task task = new Task();
        task.setName(mEdtName.getText().toString().trim());
        task.setDueDate(mEdtDuaDate.getText().toString().trim());
        task.setNote(mEdtNote.getText().toString().trim());
        task.setPriority(mSpnPriority.getSelectedItem().toString());
        task.setStatus(mSpnStatus.getSelectedItem().toString());
        try {
            mDatabaseHelper.createOrUpdate(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTask() {
        mTask.setName(mEdtName.getText().toString().trim());
        mTask.setDueDate(mEdtDuaDate.getText().toString().trim());
        mTask.setNote(mEdtNote.getText().toString().trim());
        mTask.setPriority(mSpnPriority.getSelectedItem().toString());
        mTask.setStatus(mSpnStatus.getSelectedItem().toString());
        try {
            mDatabaseHelper.createOrUpdate(mTask);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSpiner() {
        // priority
        priority = getResources().getStringArray(R.array.priority);
        CustomSpinerAdapter customSpinerPriority = new CustomSpinerAdapter(getBaseContext(), priority);
        mSpnPriority.setAdapter(customSpinerPriority);
        // status
        status = getResources().getStringArray(R.array.status);
        CustomSpinerAdapter customSpinerStatus = new CustomSpinerAdapter(getBaseContext(), status);
        mSpnStatus.setAdapter(customSpinerStatus);
    }

    private void setValue() {
        mEdtName.setText(mTask.getName());
        mEdtDuaDate.setText(mTask.getDueDate());
        mEdtNote.setText(mTask.getNote());
        int iPriority = Arrays.asList(priority).indexOf(mTask.getPriority());
        mSpnPriority.setSelection(iPriority);
        int iStatus = Arrays.asList(status).indexOf(mTask.getStatus());
        mSpnStatus.setSelection(iStatus);
    }
}
