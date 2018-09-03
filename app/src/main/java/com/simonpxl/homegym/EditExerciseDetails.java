package com.simonpxl.homegym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.simonpxl.homegym.Sqlite.DatabaseHelper;
import com.simonpxl.homegym.Sqlite.Exercise;

import java.util.logging.Logger;

public class EditExerciseDetails extends AppCompatActivity {
    public EditText mEditName, mEditTargetArea, mEditSets, mEditReps, mEditWeight;
    //public Spinner mTargetArea;
    //private Context context;
    Button button;
    DatabaseHelper db;

    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "saveNewName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPreferences();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(this);

        if (toolbar != null) {
            getSupportActionBar().setTitle("Edit Exercise Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                String previousName = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mEditName.setText(previousName);
            }
        }

        Intent intent = getIntent();
        if (intent.hasExtra("Id")) {
            int id = intent.getIntExtra("Id", 1);
            Exercise ex = db.getExercise(id);
            mEditName = (EditText) findViewById(R.id.edit_name);
            mEditTargetArea = (EditText) findViewById(R.id.edit_targetArea);

            //mTargetArea = (Spinner) findViewById(R.id.spinner);
            mEditSets = (EditText) findViewById(R.id.edit_sets);
            mEditReps = (EditText) findViewById(R.id.edit_reps);
            mEditWeight = (EditText) findViewById(R.id.edit_weight);

            mEditName.setText(String.valueOf(ex.getName()));
            //new MyTask().execute(String.valueOf(ex.getTargetArea()));
            mEditTargetArea.setText(String.valueOf(ex.getTargetArea()));
            mEditSets.setText(String.valueOf(ex.getSets()));
            mEditReps.setText(String.valueOf(ex.getReps()));
            mEditWeight.setText(String.valueOf(ex.getWeight()));

            button = (Button) findViewById(R.id.btn_save);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sets, reps, weight;
                    try {
                        sets = Integer.parseInt(mEditSets.getText().toString());
                        reps = Integer.parseInt(mEditReps.getText().toString());
                        weight = Integer.parseInt(mEditWeight.getText().toString());
                        Exercise updatedExercise = new Exercise(ex.getId(), mEditName.getText().toString(),
                                mEditTargetArea.getText().toString(), sets, reps, weight);
                        db.updateExercise(updatedExercise);
                    } catch (NumberFormatException ex) {
                        Log.e("Edit", "onClick: update exercise failed");
                        System.out.println(ex);
                    }
                    Intent intent = new Intent(EditExerciseDetails.this, ExerciseDetails.class);
                    intent.putExtra("Id", ex.getId());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String name = mEditName.getText().toString();
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(EditExerciseDetails.this, ExerciseDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void setPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean setTheme = preferences.getBoolean("colourScheme", true);
        if (setTheme == true){
            setTheme(R.style.AppTheme_Dark);
        }
        else{
            setTheme(R.style.AppTheme);
        }
    }

//    private class MyTask extends AsyncTask<String, Integer, String>{
//        @Override
//        protected String doInBackground(String... params) {
//            String[] areas = new String[]{"Legs", "Chest", "Full Body", "Upper Body", "Cardio"};
//            ArrayAdapter<String> adapter;
//            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, areas);
//            mTargetArea.setAdapter(adapter);
//            return null;
//        }
//    }
//
}

