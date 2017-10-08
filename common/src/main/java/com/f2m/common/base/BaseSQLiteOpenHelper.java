package com.f2m.common.base;

import android.content.Context;

import com.f2m.common.managers.AppManager;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by sev_user on 9/28/2017.
 */

public abstract class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

    private String mKey = AppManager.getInstance().getApplicationSQLiteEncryptKey();

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabase getReadableDatabase() {
        return this.getReadableDatabase(mKey);
    }

    public SQLiteDatabase getWritableDatabase() {
        return this.getReadableDatabase(mKey);
    }

    public abstract String getDatabaseName();
    public abstract int getDatabseVersion();
}
