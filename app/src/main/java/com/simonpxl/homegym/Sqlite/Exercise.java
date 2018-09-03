package com.simonpxl.homegym.Sqlite;

import android.content.Intent;

public class Exercise {
    public static final String TABLE_NAME = "exercises";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TARGETAREA = "targetArea";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_WEIGHT = "weight";


    private String name, targetArea;
    private Integer id, sets, reps, weight;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TARGETAREA + " TEXT,"
            + COLUMN_SETS + " INTEGER,"
            + COLUMN_REPS + " INTEGER,"
            + COLUMN_WEIGHT + " INTEGER"
            + ")";

    public Exercise(){

    }

    public Exercise(Integer id, String name, String targetArea, Integer sets, Integer reps, Integer weight)
    {
        this.id = id;
        this.name = name;
        this.targetArea = targetArea;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public Exercise( String name, String targetArea, Integer sets, Integer reps, Integer weight)
    {
        this.name = name;
        this.targetArea = targetArea;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTargetArea(){
        return targetArea;
    }

    public void setTargetArea(String targetArea){
        this.targetArea = targetArea;
    }

    public Integer getSets(){
        return sets;
    }

    public void setSets(Integer sets){
        this.sets = sets;
    }

    public Integer getReps(){
        return reps;
    }

    public void setReps(Integer reps){
        this.reps = reps;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight){
        this.weight = weight;
    }
}
