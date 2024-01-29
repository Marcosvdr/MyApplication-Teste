package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BancoMovelDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bancomovel.db";

    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    public BancoMovelDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void copyDatabaseFromAssets() throws IOException {

        String outFileName = mContext.getDatabasePath(DATABASE_NAME).getPath();
        InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);

        try (OutputStream outputStream = Files.newOutputStream(Paths.get(outFileName))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
        inputStream.close();
    }
}
