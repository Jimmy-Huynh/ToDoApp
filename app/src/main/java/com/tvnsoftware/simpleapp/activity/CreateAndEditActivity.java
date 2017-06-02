package com.tvnsoftware.simpleapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.tvnsoftware.simpleapp.R;
import com.tvnsoftware.simpleapp.model.Task;
import com.tvnsoftware.simpleapp.utils.CustomSpinerAdapter;
import com.tvnsoftware.simpleapp.utils.DatabaseHelper;

import java.sql.SQLException;

public class CreateAndEditActivity extends AppCompatActivity {
    private EditText mEdtName, mEdtDuaDate, mEdtNote;
    private Spinner mSpnPriority, mSpnStatus;
    private DatabaseHelper mDatabaseHelper;

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
        findViewById(R.id.btn_add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // priority
        String[] priority = null;
        priority = getResources().getStringArray(R.array.priority);
        CustomSpinerAdapter customSpinerPriority = new CustomSpinerAdapter(getBaseContext(), priority);
        mSpnPriority.setAdapter(customSpinerPriority);
        mSpnPriority.setSelection(0);
        // status
        String[] status = null;
        status = getResources().getStringArray(R.array.status);
        CustomSpinerAdapter customSpinerStatus = new CustomSpinerAdapter(getBaseContext(), status);
        mSpnStatus.setAdapter(customSpinerStatus);
        mSpnStatus.setSelection(0);

    }

    private void addTask() {
        Task task = new Task();
        task.setName(mEdtName.getText().toString().trim());
        task.setNote(mEdtNote.getText().toString().trim());
        task.setPriority(mSpnPriority.getSelectedItem().toString());
        task.setStatus(mSpnStatus.getSelectedItem().toString());
        try {
            mDatabaseHelper.createOrUpdate(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
