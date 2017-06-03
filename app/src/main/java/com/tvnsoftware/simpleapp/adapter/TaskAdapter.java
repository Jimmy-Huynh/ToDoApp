package com.tvnsoftware.simpleapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tvnsoftware.simpleapp.R;
import com.tvnsoftware.simpleapp.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by TamHH on 6/1/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mTasks;
    private Context mContext;
    private TaskListener mListener;
    private String[] priority = null;
    private int mIHighColor;
    private int mIMediumColor;
    private int mISlowColor;

    public interface TaskListener {
        public void onClickTask(Task task);
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        final Task task = mTasks.get(position);
        int iPriority = Arrays.asList(priority).indexOf(task.getPriority());
        if (0 == iPriority) {
            holder.mTvTaskPriority.setTextColor(mIHighColor);
        } else if (1 == iPriority) {
            holder.mTvTaskPriority.setTextColor(mIMediumColor);
        } else {
            holder.mTvTaskPriority.setTextColor(mISlowColor);
        }
        holder.mTvTaskName.setText(task.getName());
        holder.mTvTaskPriority.setText(mTasks.get(position).getPriority());
        holder.mLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickTask(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public Context getContext() {
        return mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTaskName, mTvTaskPriority;
        private LinearLayout mLayoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTaskName = (TextView) itemView.findViewById(R.id.list_item);
            mTvTaskPriority = (TextView) itemView.findViewById(R.id.tv_priority);
            mLayoutMain = (LinearLayout) itemView.findViewById(R.id.layout_main);
        }
    }

    public TaskAdapter(Context context, TaskListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mTasks = new ArrayList<>();
        priority = context.getResources().getStringArray(R.array.priority);
        mIHighColor = ContextCompat.getColor(context, R.color.gender_press);
        mIMediumColor = ContextCompat.getColor(context, R.color.main_blue);
        mISlowColor = ContextCompat.getColor(context, R.color.colorPrimaryDark);
    }

    public void appendDatas(List<Task> tasks) {
        this.mTasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void appendData(Task task) {
        this.mTasks.add(task);
        notifyDataSetChanged();
    }

    public void addNewData(List<Task> tasks) {
        this.mTasks = tasks;
        notifyDataSetChanged();
    }
}
