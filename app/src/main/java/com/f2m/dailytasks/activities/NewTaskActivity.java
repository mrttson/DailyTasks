package com.f2m.dailytasks.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.f2m.common.base.BaseActivity;
import com.f2m.common.utils.CommonUltil;
import com.f2m.dailytasks.R;
import com.f2m.dailytasks.activities.adapters.EventTypeOptions;
import com.f2m.dailytasks.activities.adapters.ReminderAdapter;
import com.f2m.dailytasks.activities.adapters.ReminderOptions;
import com.f2m.dailytasks.activities.adapters.RepeatOptions;
import com.f2m.dailytasks.database.SQLiteDBHelper;
import com.f2m.dailytasks.task.TaskInfo;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sev_user on 9/28/2017.
 */

public class NewTaskActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = CommonUltil.buildTag(NewTaskActivity.class);

    private SQLiteDBHelper dbHelper;

    private EditText edtTaskTitle;
    private TextView textAllDay;
    private CheckBox cbAllDay;
    private LinearLayout btnChangeStart;
    private TextView textStart;
    private TextView valueStart;
    private LinearLayout btnChangeEnd;
    private TextView textEnd;
    private TextView valueEnd;

//    private LinearLayout notificationsContainer;
    private ListView lvNotifications;
    private Button btnAddNotification;
    private EditText edtNotes;
//    private ListView lvRepeats;
    private TextView tvRepeat;
    private Button btnSelectRepeats;
    private TextView textType;
    private Button btnSelectType;

    private ReminderAdapter mReminderAdapter;
    private List<ReminderOptions> selectedReminderOptions = new ArrayList<>(ReminderOptions.DefaultOptions);
    private RepeatOptions selectedRepeatOption = RepeatOptions.NO_REPEAT;

    private EventTypeOptions selectedEventTypeOptions = EventTypeOptions.TYPE_DEFAULT;

    private Calendar mCalendar = Calendar.getInstance();
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MMM d, yy hh:mm aa");
    private long timeStart = -1;
    private long timeEnd = -1;

    TimePickerDialog.OnTimeSetListener mOnStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            String dateString = mSimpleDateFormat.format(mCalendar.getTime());
            timeStart = mCalendar.getTimeInMillis();
            valueStart.setText(dateString);
        }
    };

    TimePickerDialog.OnTimeSetListener mOnEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            String dateString = mSimpleDateFormat.format(mCalendar.getTime());
            timeEnd = mCalendar.getTimeInMillis();
            valueEnd.setText(dateString);
        }
    };

    DatePickerDialog.OnDateSetListener mOnStartDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(
                    NewTaskActivity.this,
                    mOnStartTimeSetListener,
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true
            ).show();
        }
    };

    DatePickerDialog.OnDateSetListener mOnEndDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(
                    NewTaskActivity.this,
                    mOnEndTimeSetListener,
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true
            ).show();
        }
    };

    DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            Log.d(TAG, "DataSet Changed");
            super.onChanged();

            CommonUltil.updateListViewHeightBasedOnChildren(lvNotifications);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new SQLiteDBHelper(this);
        setupElements();
    }

    private void setupElements() {
        Log.d(TAG, "setupElements");

        edtTaskTitle = (EditText)findViewById(R.id.edtTaskTitle);
        // setup for duration
        mCalendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        mCalendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        mCalendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        mCalendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        mCalendar.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
        // label for duration
        textAllDay = (TextView)findViewById(R.id.textAllDay);
        cbAllDay = (CheckBox)findViewById(R.id.cbAllDay);
        cbAllDay.setOnCheckedChangeListener(this);
        btnChangeStart = (LinearLayout)findViewById(R.id.btnChangeStart);
        btnChangeStart.setOnClickListener(this);
        textStart = (TextView)findViewById(R.id.textStart);
        valueStart = (TextView)findViewById(R.id.valueStart);
        valueStart.setText(mSimpleDateFormat.format(mCalendar.getTime()));
        btnChangeEnd=(LinearLayout)findViewById(R.id.btnChangeEnd);
        btnChangeEnd.setOnClickListener(this);
        textEnd = (TextView)findViewById(R.id.textEnd);
        valueEnd = (TextView) findViewById(R.id.valueEnd);
        valueEnd.setText(mSimpleDateFormat.format(mCalendar.getTime()));
        // notifications
        lvNotifications = (ListView)findViewById(R.id.lvNotifications);
        mReminderAdapter = new ReminderAdapter(this, selectedReminderOptions);
        mReminderAdapter.registerDataSetObserver(mDataSetObserver);
        lvNotifications.setAdapter(mReminderAdapter);

        //notificationsContainer = (LinearLayout) findViewById(R.id.notificationsContainer);
        btnAddNotification = (Button)findViewById(R.id.btnAddNotification);
        btnAddNotification.setOnClickListener(this);

        edtNotes = (EditText)findViewById(R.id.edtNotes);
//        lvRepeats = (ListView)findViewById(R.id.lvRepeat);
        tvRepeat = (TextView)findViewById(R.id.tvRepeat);
        tvRepeat.setText(selectedRepeatOption.getTitle());
        btnSelectRepeats = (Button)findViewById(R.id.btnSelectRepeat);
        btnSelectRepeats.setOnClickListener(this);

        textType = (TextView)findViewById(R.id.textType);
        btnSelectType = (Button)findViewById(R.id.btnSelectType);
        btnSelectType.setOnClickListener(this);

        updateAllDayEnable(isAllDayEnabled());
    }

    private void updateAllDayEnable(boolean isEnabled) {
        Log.d(TAG, "updateAllDayEnable >> " + isEnabled);

        //textAllDay.setEnabled(isEnabled);
        textStart.setEnabled(!isEnabled);
        valueStart.setEnabled(!isEnabled);
        textEnd.setEnabled(!isEnabled);
        valueEnd.setEnabled(!isEnabled);
    }

    private void addNewTask() {
        Log.d(TAG, "addNewTask");

        if (!isValidData())
            return;

        String title = edtTaskTitle.getText().toString();
        String notes = edtNotes.getText().toString();

        // fill data to task
        TaskInfo task = new TaskInfo();
        task.setTitle(title);
        task.setTimeStart(timeStart);
        task.setTimeEnd(timeEnd);
        task.setType(selectedEventTypeOptions);
        task.setNotes(notes);
        task.setReminders(selectedReminderOptions);
        task.setRepeat(selectedRepeatOption);

        dbHelper.addTask(task);
        finish();
    }

    private boolean isValidData() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_save:
                addNewTask();
                finish();
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnChangeStart:
                if (!isAllDayEnabled()) {
                    new DatePickerDialog(
                            this,
                            mOnStartDateSetListener,
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                    ).show();
                }
                break;

            case R.id.btnChangeEnd:
                if (!isAllDayEnabled()) {
                    new DatePickerDialog(
                            this,
                            mOnEndDateSetListener,
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                    ).show();
                }
                break;
            case R.id.btnAddNotification:
                showDialogSelectNotification();
                break;

            case R.id.btnSelectRepeat:
                showDialogSelectRepeat();
                break;

            case R.id.btnSelectType:
                showDialogSelectType();
                break;
        }
    }

    private void showDialogSelectType() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Option");

        final ArrayAdapter<EventTypeOptions> adapter = new ArrayAdapter<EventTypeOptions>(this, R.layout.select_dialog_singlechoice_with_image, R.id.textType){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View rowView = super.getView(position, convertView, parent);
                CheckedTextView title = (CheckedTextView)rowView.findViewById(R.id.textType);
                title.setText(EventTypeOptions.AllOptions.get(position).getTitle());
                ImageView icon = (ImageView)rowView.findViewById(R.id.iconType);
                Picasso.with(NewTaskActivity.this)
                        .load(EventTypeOptions.AllOptions.get(position).getIcon())
                        .into(icon);
                return rowView;
            }
        };
        adapter.addAll(EventTypeOptions.AllOptions);

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedEventTypeOptions = EventTypeOptions.AllOptions.get(which);
                textType.setText(selectedEventTypeOptions.getTitle());
            }
        });
        dialogBuilder.show();
    }

    private void showDialogSelectRepeat() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Option");

        final ArrayAdapter<RepeatOptions> adapter = new ArrayAdapter<RepeatOptions>(this, android.R.layout.select_dialog_singlechoice){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View rowView = super.getView(position, convertView, parent);
                CheckedTextView title = (CheckedTextView)rowView.findViewById(android.R.id.text1);
                title.setText(RepeatOptions.AllOptions.get(position).getTitle());
                return rowView;
            }
        };
        adapter.addAll(RepeatOptions.AllOptions);

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedRepeatOption = RepeatOptions.AllOptions.get(which);
                tvRepeat.setText(selectedRepeatOption.getTitle());
            }
        });
        dialogBuilder.show();
    }

    private void showDialogSelectNotification() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Option");

        final ArrayAdapter<ReminderOptions> adapter = new ArrayAdapter<ReminderOptions>(this, android.R.layout.select_dialog_singlechoice){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View rowView = super.getView(position, convertView, parent);
                CheckedTextView title = (CheckedTextView)rowView.findViewById(android.R.id.text1);
                title.setText(ReminderOptions.AllOptions.get(position).getTitle());
                return rowView;
            }
        };
        adapter.addAll(ReminderOptions.AllOptions);

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReminderOptions selected = ReminderOptions.AllOptions.get(which);
                if (selected == ReminderOptions.OPTION_NO_REMIND) {
                    selectedReminderOptions.clear();
                    selectedReminderOptions.add(ReminderOptions.OPTION_NO_REMIND);
                } else {
                    if (selectedReminderOptions.contains(ReminderOptions.OPTION_NO_REMIND)) {
                        selectedReminderOptions.remove(ReminderOptions.OPTION_NO_REMIND);
                    }
                    if (!selectedReminderOptions.contains(selected)) {
                        selectedReminderOptions.add(selected);
                    }
                }
                mReminderAdapter.notifyDataSetChanged();
            }
        });
        dialogBuilder.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.cbAllDay:
                updateAllDayEnable(isChecked);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReminderAdapter.unregisterDataSetObserver(mDataSetObserver);
    }

    private boolean isAllDayEnabled() {
        return cbAllDay != null && cbAllDay.isChecked();
    }
}
