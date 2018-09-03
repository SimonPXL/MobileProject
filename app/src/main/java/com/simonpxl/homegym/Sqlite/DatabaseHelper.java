package com.simonpxl.homegym.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //DB Version
    private static final int DATABASE_VERSION = 1;

    //DB Name
    private static final String DATABASE_NAME = "Exercises.db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create exercises table
        db.execSQL(Exercise.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Exercise.TABLE_NAME);

        onCreate(db);
    }

    public long addExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //ID will be automatically added
        values.put(Exercise.COLUMN_NAME, exercise.getName());
        values.put(Exercise.COLUMN_TARGETAREA, exercise.getTargetArea());
        values.put(Exercise.COLUMN_SETS, exercise.getSets());
        values.put(Exercise.COLUMN_REPS, exercise.getReps());
        values.put(Exercise.COLUMN_WEIGHT, exercise.getWeight());

        //Insert Row
        long id = db.insert(Exercise.TABLE_NAME, null, values);

        Log.d("Add Exercise", "AddExercise: " + exercise.getName());

        //Close DB Connection
        db.close();

        return id;
    }

    public Exercise getExercise(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Exercise.TABLE_NAME,
                new String[]{Exercise.COLUMN_ID, Exercise.COLUMN_NAME, Exercise.COLUMN_TARGETAREA,
                Exercise.COLUMN_SETS, Exercise.COLUMN_REPS, Exercise.COLUMN_WEIGHT}, Exercise.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Exercise exercise = new Exercise(cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_TARGETAREA)),
                cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_SETS)), cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_REPS)),
                cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_WEIGHT)));

        Log.d("Get Exercise", "getExercise: " + exercise.getName());
        //Close DB Connection
        cursor.close();

        return exercise;
    }

    public List<Exercise> getAllExercises(){
        List<Exercise> exercises = new ArrayList<>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + Exercise.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_ID)));
                exercise.setName(cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_NAME)));
                exercise.setTargetArea(cursor.getString(cursor.getColumnIndex(Exercise.COLUMN_TARGETAREA)));
                exercise.setSets(cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_SETS)));
                exercise.setReps(cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_REPS)));
                exercise.setWeight(cursor.getInt(cursor.getColumnIndex(Exercise.COLUMN_WEIGHT)));

                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        db.close();

        return exercises;
    }

    public int updateExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Exercise.COLUMN_NAME, exercise.getName());
        values.put(Exercise.COLUMN_TARGETAREA, exercise.getTargetArea());
        values.put(Exercise.COLUMN_SETS, exercise.getSets());
        values.put(Exercise.COLUMN_REPS, exercise.getReps());
        values.put(Exercise.COLUMN_WEIGHT, exercise.getWeight());

        Log.i("update", "updateExercise: " + exercise.getName());
        //Updating Row
        return db.update(Exercise.TABLE_NAME, values, Exercise.COLUMN_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});

    }

    public void deleteExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Exercise.TABLE_NAME, Exercise.COLUMN_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});

        Log.i("Delete", "deleteExercise: " + exercise.getName());
        db.close();
    }
}
