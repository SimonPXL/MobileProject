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
        import android.widget.TextView;

        import com.simonpxl.homegym.Sqlite.DatabaseHelper;
        import com.simonpxl.homegym.Sqlite.Exercise;

public class ExerciseDetails extends AppCompatActivity{

    public TextView mNameText, mTargetAreaText, mSetsText, mRepsText, mWeightText;
    private DatabaseHelper db;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPreferences();

        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        setContentView(R.layout.activity_exercise_details);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Exercise Details");

        Intent intent = getIntent();
        if (intent.hasExtra("Id")){
            int id = intent.getIntExtra("Id", 1);
            Exercise exercise = db.getExercise(id);
            mTargetAreaText = (TextView) findViewById(R.id.tv_targetArea);
            mNameText = (TextView) findViewById(R.id.tv_name);
            mSetsText = (TextView) findViewById(R.id.tv_sets);
            mRepsText = (TextView) findViewById(R.id.tv_reps);
            mWeightText = (TextView) findViewById(R.id.tv_weight);
            mNameText.setText("Exercise: " + String.valueOf(exercise.getName()));
            mTargetAreaText.setText("Target Area: " + String.valueOf(exercise.getTargetArea()));
            mSetsText.setText("Sets: " + String.valueOf(exercise.getSets()));
            mRepsText.setText("Reps: " + String.valueOf(exercise.getReps()));
            mWeightText.setText("Weight: " + String.valueOf(exercise.getWeight()));

            button = (Button) findViewById(R.id.btn_edit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExerciseDetails.this, EditExerciseDetails.class);
                    intent.putExtra("Id", exercise.getId());
                    startActivity(intent);
                }
            });
        }
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
                Intent intent = new Intent(ExerciseDetails.this, MainActivity.class);
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
            case R.id.action_main:
                Intent startMainActivity = new Intent(this, MainActivity.class);
                startActivity(startMainActivity);
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
