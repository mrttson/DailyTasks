package com.f2m.dailytasks.activities.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.f2m.common.utils.CommonUltil;
import com.f2m.dailytasks.R;
import com.f2m.dailytasks.task.TaskInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 9/27/2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> implements View.OnTouchListener {

    private List<TaskInfo> listTasks = new ArrayList<>();

    private int selectedTaskBefore = -1;
    private int selectedTask = -1;

    private OnItemClickListener mOnItemClickListener = null;

    private Context context;
    public TasksAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        TaskViewHolder holder = new TaskViewHolder(v);
        holder.itemView.setOnTouchListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        if (position < listTasks.size()) {
            TaskInfo info = listTasks.get(position);

            String title = info.getTitle();
            title = title!= null ? title : "Empty title ...";
            String notes = info.getNotes();
            notes = notes != null ? notes : "Nothing to notes ...";
            EventTypeOptions type = info.getType();
            type = type != null ? type : EventTypeOptions.TYPE_DEFAULT;

            holder.tvTaskTitle.setText(title);
            holder.tvTaskDetails.setText(notes);
            Picasso.with(context)
                    .load(type.getIcon())
                    .into(holder.iconType);

//            if(mOnItemClickListener != null) {
//
//                if (position == selectedTask)
//                    holder.setSelectedState(true);
//                else holder.setSelectedState(false);
//
//                holder.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mOnItemClickListener.onItemClick(holder);
//                    }
//                });
//
//
//            }
        }
    }

    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    public List<TaskInfo> getListTasks() {
        return listTasks;
    }

    public void setListTasks(List<TaskInfo> listTasks) {
        this.listTasks = listTasks;
    }

    public int getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(int selectedTask) {
        selectedTaskBefore = this.selectedTask;
        this.selectedTask = selectedTask;
        notifyItemChanged(selectedTaskBefore);
        notifyItemChanged(this.selectedTask);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        notifyDataSetChanged();
    }


    /*
        Implement slide item task
     */

    float originX;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                originX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float offsetX = x - originX;
//                if (offsetX > 0 && offsetX < ) {
//
//                }
                break;
        }


        return true;
    }
}
