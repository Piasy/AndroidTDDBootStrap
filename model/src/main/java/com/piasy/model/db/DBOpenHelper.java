package com.piasy.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import com.piasy.model.dao.GithubUserTableMeta;

/**
 * Created by piasy on 15/8/10.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Template_db";

    private static final int VERSION = 1;

    public DBOpenHelper(@NonNull Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    // Better than static final field -> allows VM to unload useless String
    // Because you need this string only once per application life on the device

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GithubUserTableMeta.getCreateGithubUserTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no impl
    }
}
