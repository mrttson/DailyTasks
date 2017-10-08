package com.f2m.dailytasks.activities.adapters;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f2m.dailytasks.R;

/**
 * Created by sev_user on 9/27/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

    private int textColor = Color.parseColor("#6c6c6c");
    private int textColorSelected = Color.WHITE;

    public TextView tvTaskTitle;
    public RelativeLayout taskItemWrapper;
    //public ImageView btnGotoTaskDetails;
    public TextView tvTaskDetails;
    public ImageView iconType;

    private View.OnClickListener mOnClickListener = null;


    public TaskViewHolder(View itemView) {
        super(itemView);
        itemView.setOnTouchListener(this);

        taskItemWrapper = (RelativeLayout) itemView.findViewById(R.id.taskItemWrapper);
        tvTaskTitle = (TextView) itemView.findViewById(R.id.edtTaskTitle);
        tvTaskDetails = (TextView) itemView.findViewById(R.id.taskDetails);
        //btnGotoTaskDetails = (ImageView) itemView.findViewById(R.id.btnGotoTaskDetails);
        iconType = (ImageView)itemView.findViewById(R.id.imgvTaskIcon);
    }


    public void setSelectedState(boolean isSelected) {

        if (isSelected) {
            taskItemWrapper.setBackgroundResource(R.drawable.bg_task_info_wrapper_selected);
            tvTaskTitle.setTextColor(textColorSelected);
            tvTaskDetails.setTextColor(textColorSelected);
            //btnGotoTaskDetails.setImageResource(R.drawable.ic_goto_details_selected);
        }
        else  {
            taskItemWrapper.setBackgroundResource(R.drawable.bg_task_info_wrapper);
            tvTaskTitle.setTextColor(textColor);
            tvTaskDetails.setTextColor(textColor);
            //btnGotoTaskDetails.setImageResource(R.drawable.ic_goto_details);
        }
    }

    public void setOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
