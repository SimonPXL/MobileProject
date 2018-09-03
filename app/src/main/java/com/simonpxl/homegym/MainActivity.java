package com.simonpxl.homegym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.simonpxl.homegym.Sqlite.DatabaseHelper;
import com.simonpxl.homegym.Sqlite.Exercise;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener{
    private List<Exercise> exerciseList = new ArrayList<>();
    private ExerciseAdapter mAdapter;
    private RecyclerView recyclerView;
    private DatabaseHelper db;

    protected void onCreate(Bundle savedInstanceState) {
        setPreferences();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        exerciseList = db.getAllExercises();
        recyclerView = (RecyclerView) findViewById(R.id.rv_exercises);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new ExerciseAdapter(exerciseList, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareExercises();

        }

    private void setPreferences() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean setTheme = pref.getBoolean("colourScheme", true);
        if (setTheme == true){
            setTheme(R.style.AppTheme_Dark);
        }
        else{
            setTheme(R.style.AppTheme);
        }
    }

    private void prepareExercises(){
//        Exercise exercise = new Exercise(1, "Squats", "Legs", 5, 5, 80);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(2, "Plank", "Upper Body", 3, 30, 0);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(3, "Deadlift", "Legs", 5, 5, 80);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(4, "Push ups", "Upper Body", 3, 20, 0);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(5, "Pull ups", "Upper Body", 3, 10, 20);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(6, "Side Plank", "Upper Body", 3, 30, 0);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(7, "Lunges", "Legs", 3, 15, 0);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(8, "Bench Press", "Upper Body", 5, 5, 80);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(9, "Running", "Cardio", 3, 10, 0);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);
//
//        exercise = new Exercise(10, "Muay Thai", "Full Body", 1, 90, 0);
////        exerciseList1.add(exercise);
//        db.AddExercise(exercise);

        exerciseList = db.getAllExercises();

        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(MainActivity.this, ExerciseDetails.class);
        int id = exerciseList.get(position).getId();
        intent.putExtra("Id", id);
        startActivity(intent);
    }
}
