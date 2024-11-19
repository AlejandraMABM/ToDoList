package com.example.todolist.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.data.Task

class DataBaseManager (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    // If you change the database schema, you must increment the database version.
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ToDoListDatabase.db"


        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${Task.TABLE_NAME} (" +
                    "${Task.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Task.COLUM_NAME} TEXT," +
                    "${Task.COLUMN_DONE} INTEGER)"

        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${Task.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    private fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE)

    }


}