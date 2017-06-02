package com.tvnsoftware.simpleapp.activity;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tvnsoftware.simpleapp.R;
import com.tvnsoftware.simpleapp.contants.AppContants;
import com.tvnsoftware.simpleapp.dialog.DialogMessage;
import com.tvnsoftware.simpleapp.model.Task;
import com.tvnsoftware.simpleapp.utils.DatabaseHelper;

public class ViewTaskActivity extends AppCompatActivity {
    private Task mTask;
    private TextView mTvName, mTvDueDate, mTvNote, mTvPriority, mTvStatus;
    private Button mBtnEdit, mBtnDelete;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        mDatabaseHelper = new DatabaseHelper(this);
        mBtnEdit = (Button) findViewById(R.id.btn_edit);
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvDueDate = (TextView) findViewById(R.id.tv_due_date);
        mTvNote = (TextView) findViewById(R.id.tv_note);
        mTvPriority = (TextView) findViewById(R.id.tv_priority);
        mTvStatus = (TextView) findViewById(R.id.tv_status);

        mTask = (Task) getIntent().getSerializableExtra(AppContants.OBJECT_TASK);
        if (null != mTask) {
            mTvName.setText(mTask.getName());
            mTvNote.setText(mTask.getNote());
            mTvPriority.setText(mTask.getPriority());
            mTvStatus.setText(mTask.getStatus());
        }
    }

    private void confirm() {
        DialogMessage dialogMessage = new DialogMessage(this, new DialogMessage.DialogMessageListener() {
            @Override
            public void onButtonClick() {
                delete();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        dialogMessage.showDialog("Do you want to delete?");
    }

    private void delete() {
        try {
            mDatabaseHelper.deleteById(Task.class, mTask.getId());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

}
