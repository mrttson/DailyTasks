package com.f2m.dailytasks.database;

import android.content.ContentValues;
import android.content.Context;

import com.f2m.common.base.BaseSQLiteOpenHelper;
import com.f2m.dailytasks.activities.adapters.ReminderOptions;
import com.f2m.dailytasks.task.TaskInfo;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by sev_user on 9/28/2017.
 */

public class SQLiteDBHelper extends BaseSQLiteOpenHelper {

    private static final String DATABASE_NAME = "DailyTasks";
    private static final int DATABASE_VERSION = 1;

    // tables Tasks
    private static final String TABLE_TASKS = "tasks";
    private static final String FIELD_ID = "id";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_NOTES = "notes";
    private static final String FIELD_START_TIME = "start_time";
    private static final String FIELD_END_TIME = "end_time";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_REMINDERS = "reminders";

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public int getDatabseVersion() {
        return DATABASE_VERSION;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASKS + "("
                + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
                + "," + FIELD_TITLE + " TEXT"
                + "," + FIELD_NOTES + " TEXT"
                + "," + FIELD_START_TIME + " TEXT"
                + "," + FIELD_END_TIME + " TEXT"
                + "," + FIELD_TYPE + " TEXT"
                + "," + FIELD_REMINDERS + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not implement yet
    }

    public List<TaskInfo> getAllTasks() {
        List<TaskInfo> listTasks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, null, null, null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskInfo task = new TaskInfo();
                task.setId(cursor.getInt(0));
                task.setTitle(cursor.getString(1));
                listTasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listTasks;
    }

    public TaskInfo getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS,
                null,
                FIELD_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        TaskInfo task = null;
        if (cursor != null && cursor.moveToFirst()) {
            task = new TaskInfo();
            task.setId(cursor.getInt(0));
            task.setTitle(cursor.getString(1));
        }

        return task;
    }

    public void addTask(final TaskInfo task) {
        addTasks(Collections.singletonList(task));
    }

    public void addTasks(List<TaskInfo> tasks) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        for (TaskInfo task :
                tasks) {
            ContentValues values = new ContentValues();
            values.put(FIELD_TITLE, task.getTitle());
            values.put(FIELD_NOTES, task.getNotes());
            values.put(FIELD_START_TIME, task.getTimeStart());
            values.put(FIELD_END_TIME, task.getTimeEnd());
            values.put(FIELD_TYPE, task.getType().toString());

            List<String> listReminders = Lists.transform(task.getReminders(), new Function<ReminderOptions, String>() {
                @Nullable
                @Override
                public String apply(@Nullable ReminderOptions input) {
                    if (input == null)
                        return null;
                    return input.toString();
                }
            });
            //listReminders = new ArrayList<>();
            String strReminders = Joiner.on(',').join(listReminders);
            values.put(FIELD_REMINDERS, strReminders);
            // Inserting Row
            db.insert(TABLE_TASKS, null, values);
        }

        db.endTransaction();
        db.close();
    }
}
