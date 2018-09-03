package com.simonpxl.homegym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simonpxl.homegym.Sqlite.DatabaseHelper;
import com.simonpxl.homegym.Sqlite.Exercise;

public class AddExercise extends AppCompatActivity {
    public EditText mAddName, mAddTargetArea, mAddSets, mAddReps, mAddWeight;
    Button button;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPreferences();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        db = new DatabaseHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Exercise");

        mAddName = (EditText) findViewById(R.id.add_name);
        mAddTargetArea = (EditText) findViewById(R.id.add_targetArea);
        mAddSets = (EditText) findViewById(R.id.add_sets);
        mAddReps = (EditText) findViewById(R.id.add_reps);
        mAddWeight = (EditText) findViewById(R.id.add_weight);

        button = (Button) findViewById(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sets, reps, weight;
                try {
                    sets = Integer.parseInt(mAddSets.getText().toString());
                    reps = Integer.parseInt(mAddReps.getText().toString());
                    weight = Integer.parseInt(mAddWeight.getText().toString());
                    Exercise exercise = new Exercise(mAddName.getText().toString(),
                            mAddTargetArea.getText().toString(), sets, reps, weight);
                    db.addExercise(exercise);
                    Intent intent = new Intent(AddExercise.this, MainActivity.class);
                        startActivity(intent);
                } catch (NumberFormatException ex) {
                    System.out.println("Not a valid number!");
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(AddExercise.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_addExercise:
                Intent startAddExerciseActivity = new Intent(this, AddExercise.class);
                startActivity(startAddExerciseActivity);
                return true;
            case  R.id.action_settings:
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void setPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean setTheme = preferences.getBoolean("colourScheme", true);
        if (setTheme == true) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

}
