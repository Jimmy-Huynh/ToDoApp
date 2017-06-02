package com.tvnsoftware.simpleapp.adapter;
//import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tvnsoftware.simpleapp.R;
import com.tvnsoftware.simpleapp.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TamHH on 6/1/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mTasks;
    private Context mContext;
    private TaskListener mListener;

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
