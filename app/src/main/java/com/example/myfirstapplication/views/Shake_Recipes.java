package com.example.myfirstapplication.views;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.database.RecipeDao;
import com.example.myfirstapplication.database.RecipeDatabase;
import com.example.myfirstapplication.model.RecipeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Shake_Recipes extends AppCompatActivity implements SensorEventListener
{

    private SensorManager mSensorManager;
    Sensor mShake;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecipeDao recipeDao;
    private List<RecipeModel> recipeList = new ArrayList<>();
    private TextView randomRecipeTextView;
    private Button viewRecipeButton;
    private RecipeModel currentDisplayedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shake_recipes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        randomRecipeTextView = findViewById(R.id.randomRecipeTextView);
        viewRecipeButton = findViewById(R.id.viewRecipeButton);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mShake = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        RecipeDatabase recipeDatabase = Room.databaseBuilder(getApplicationContext(), RecipeDatabase.class, "Recipes").allowMainThreadQueries().build();
        recipeDao = recipeDatabase.recipeDao();

        fetchRecipes();

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            Log.d("SensroAcc", "The sensor is there!!!!!");
        }
        else {
            Log.d("SensroAcc", "Damn nooooooooo Daniel");
        }
        viewRecipeButton.setOnClickListener(v -> {
            if (!recipeList.isEmpty()) {
                // Get the random recipe
                int randomIndex = new Random().nextInt(recipeList.size());
                RecipeModel randomRecipe = recipeList.get(randomIndex);

                // Send the random recipe to Add_Recipy activity
                Intent intent = new Intent(Shake_Recipes.this, Add_Recipy.class);
                intent.putExtra("info", "old");
                intent.putExtra("dataId", randomRecipe); // Pass the random recipe object
                startActivity(intent);
            }
        });
    }

    private void fetchRecipes() {
        // Fetch the list of recipes from the database
        compositeDisposable.add(recipeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateRecipeList));
    }

    private void updateRecipeList(List<RecipeModel> recipes) {
        // Update the local recipe list
        recipeList = recipes;
    }

    @Override
    public final void onAccuracyChanged (Sensor mShake, int accuracy)
    {
        
    }

    @Override
    public final void onSensorChanged(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float acceleration = (float) Math.sqrt(x * x + y * y + z * z);
        if (acceleration > 15)
        {
            if (!recipeList.isEmpty()) {
                int randomIndex = new Random().nextInt(recipeList.size());
                RecipeModel randomRecipe = recipeList.get(randomIndex);
                randomRecipeTextView.setText(randomRecipe.recipeName); // Display the random recipe name
            } else {
                Log.d("Shaken", "No recipes available.");
            }
        }
    }

    @Override
    protected void onResume()
    {

        super.onResume();
        mSensorManager.registerListener(this, mShake, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}