package com.example.bdexternal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersQuery  {

    public UsersModel getUser(SQLiteDatabase db) {
        UsersModel usersModel = new UsersModel();

        String[] campos = new String[] {"id", "user","password"};

        Cursor result =db.query("users", campos, null, null, null, null, null);

        if ((result.getCount() == 0) || !result.moveToFirst()) {

        } else {
            if (result.moveToFirst()) {
                usersModel = new UsersModel(
                        result.getInt(result.getColumnIndex("id")),
                        result.getString(result.getColumnIndex("user")),
                        result.getString(result.getColumnIndex("password"))
                );
            }
        }
        return usersModel;
    }
}
