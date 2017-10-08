package com.f2m.dailytasks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f2m.common.managers.AppManager;
import com.f2m.common.utils.CommonUltil;
import com.f2m.dailytasks.R;
import com.f2m.dailytasks.activities.adapters.OnItemClickListener;
import com.f2m.dailytasks.activities.adapters.TaskViewHolder;
import com.f2m.dailytasks.activities.adapters.TasksAdapter;
import com.f2m.dailytasks.database.SQLiteDBHelper;
import com.f2m.dailytasks.task.TaskInfo;

import java.util.List;

/**
 * Created by sev_user on 9/29/2017.
 */

public class ListTasksFragment extends Fragment {

    private static final String TAG = CommonUltil.buildTag(ListTasksFragment.class);

    private RecyclerView lvTasks;
    private TasksAdapter tasksAdapter;
    private SQLiteDBHelper dbHelper;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_list_tasks, container, false);

        mContext = getContext();
        dbHelper = new SQLiteDBHelper(mContext);

        lvTasks = (RecyclerView) rootView.findViewById(R.id.lvTasks);
        lvTasks.setLayoutManager(new LinearLayoutManager(mContext));

        tasksAdapter = new TasksAdapter(getContext());
        tasksAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(TaskViewHolder holder) {
                tasksAdapter.setSelectedTask(holder.getAdapterPosition());
                tasksAdapter.notifyDataSetChanged();
            }
        });
        lvTasks.setAdapter(tasksAdapter);
        loadTasks();

        return rootView;
    }

    private void loadTasks() {
        Log.d(TAG, "loadTasks");

        List<TaskInfo> tasks = dbHelper.getAllTasks();
        Log.d(TAG, "" + tasks.size());

        tasksAdapter.setListTasks(tasks);
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");

        loadTasks();
        super.onResume();
    }
}
