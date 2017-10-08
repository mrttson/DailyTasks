package com.f2m.dailytasks.activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.f2m.dailytasks.R;

import java.util.List;

/**
 * Created by sev_user on 10/5/2017.
 */

public class ReminderAdapter extends ArrayAdapter<ReminderOptions> {

    private List<ReminderOptions> listOptions;
    private Context mContext;

    public ReminderAdapter(@NonNull Context context, @NonNull List<ReminderOptions> data) {
        super(context, R.layout.item_reminder_option, data);

        mContext = context;
        listOptions = data;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_reminder_option, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.titleOption);
        Button btnRemove = (Button) rowView.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOptions.remove(position);
                if (listOptions.size() == 0) {
                    listOptions.add(ReminderOptions.OPTION_NO_REMIND);
                }
                notifyDataSetChanged();
            }
        });

        if (listOptions!= null && position < listOptions.size()) {
            textView.setText(listOptions.get(position).getTitle());
        }
        return rowView;
    }

    @Override
    public int getCount() {
        return listOptions.size();
    }
}
