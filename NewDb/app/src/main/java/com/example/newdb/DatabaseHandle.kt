package com.example.newdb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val DB_NAME = "TestDB"
val DB_VERSIOM = 1;
val TABLE_NAME = "SectionC"
val ID = "id"
val FIRST_NAME = "FirstName"
val LAST_NAME = "LastName"
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($ID Integer PRIMARY KEY, $FIRST_NAME TEXT, $LAST_NAME TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    //Inserting (Creating) data
    fun addUser(user: Users): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.firstName)
        values.put(LAST_NAME, user.lastName)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    //get all users
    fun getAllUsers(): String {
        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    var lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))

                    allUser = "$allUser\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }
    fun getOne(lastname:String):String{
        var user = "No user found"
        var db = this.readableDatabase
        var selectOne = "Select * from $TABLE_NAME where $LAST_NAME = ? "
        db.rawQuery(selectOne, arrayOf(lastname)).use {
            if (it.moveToNext()) {
                var id = it.getString(it.getColumnIndex(ID))
                var firstName = it.getString(it.getColumnIndex(FIRST_NAME))
                var lastName = it.getString(it.getColumnIndex(LAST_NAME))

                user = "$id $firstName $lastName"
            }
        }

    return user
    }
}