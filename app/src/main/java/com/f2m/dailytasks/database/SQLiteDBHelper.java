package com.f2m.dailytasks.database;

import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.TabLayout;

import com.f2m.common.base.BaseSQLiteOpenHelper;
import com.f2m.dailytasks.task.TaskInfo;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteTransactionListener;

import java.util.ArrayList;
import java.util.List;

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
                + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FIELD_TITLE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not implement yet
    }

    public List<TaskInfo> getAllTasks() {
        List<TaskInfo> listTasks = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

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
                new String[] { FIELD_ID, FIELD_TITLE },
                FIELD_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TaskInfo task = new TaskInfo();
        task.setId(cursor.getInt(0));
        task.setTitle(cursor.getString(1));

        return task;
    }

    public void addTask(TaskInfo task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_TITLE, task.getTitle());

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public void addTasks(List<TaskInfo> tasks) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        for (TaskInfo task :
                tasks) {
            ContentValues values = new ContentValues();
            values.put(FIELD_TITLE, task.getTitle());

            // Inserting Row
            db.insert(TABLE_TASKS, null, values);
        }

        db.endTransaction();
        db.close();
    }
}
