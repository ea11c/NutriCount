package com.example.nutricount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "nutritional_info.db";
    public static final String TABLE_NAME = "Per_Gram";
    public static final String Food_ID = "Food_ID";
    public static final String Food = "Food";
    public static final String Fat = "Fat";
    public static final String Protein = "Protein";
    public static final String Calories = "Calories";
    public static final String Sodium = "Sodium";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (Food_ID INTEGER PRIMARY KEY AUTOINCREMENT, Food TEXT, Fat INTEGER, Protein INTEGER, Calories INTEGER, Sodium INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Food_name, double TotalFat, double TotalProtein, double TotalCalories, double TotalSodium, double TotalWeight){
        double fatGram = TotalFat/TotalWeight;
        double proteinGram = TotalProtein/TotalWeight;
        double caloriesGram = TotalCalories/TotalWeight;
        double sodiumGram = TotalSodium/(TotalWeight * 1000);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Food, Food_name);
        contentValues.put(Fat, fatGram);
        contentValues.put(Protein, proteinGram);
        contentValues.put(Calories, caloriesGram);
        contentValues.put(Sodium, sodiumGram);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getData(int pos){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Food, Calories, Fat, Protein, Sodium, Food_ID};
        String selection = Food_ID + " =?";
        String[] selectionArgs = {Integer.toString(pos)};
        Cursor res = db.query(TABLE_NAME,columns, selection, selectionArgs, null, null, null);

        return res;
    }

    public String getFood(int pos){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Food};
        String selection = Food_ID + " =?";
        String[] selectionArgs = {Integer.toString(pos)};
        Cursor res = db.query(TABLE_NAME,columns, selection, selectionArgs, null, null, null);
        res.moveToFirst();
        String result =  res.getString(0);
        res.close();
        return result;
    }

}
