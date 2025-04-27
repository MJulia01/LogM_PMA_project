package com.example.myfirstapplication.views;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.adapter.RecipeAdapter;
import com.example.myfirstapplication.database.RecipeDao;
import com.example.myfirstapplication.database.RecipeDatabase;
import com.example.myfirstapplication.databinding.ActivityBrowseRecipesBinding;
import com.example.myfirstapplication.model.RecipeModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Browse_Recipes extends AppCompatActivity {
    Button addButton;
    Button btnBack;
    private ActivityBrowseRecipesBinding binding;
    RecipeAdapter recipeAdapter;
    RecipeDao recipeDao;
    RecipeDatabase recipeDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseRecipesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        EdgeToEdge.enable(this);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recipeDatabase = Room.databaseBuilder(getApplicationContext(),RecipeDatabase.class,"Recipes").allowMainThreadQueries().build();
        recipeDao = recipeDatabase.recipeDao();

        compositeDisposable.add(recipeDao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Browse_Recipes.this::getData));

        Button button = (Button) findViewById(R.id.goToAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Browse_Recipes.this, Add_Recipy.class);
                intent.putExtra("info", "new");
                startActivity(intent);
            }
        });
       btnBack = findViewById(R.id.btnBack);
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
    }

    private void getData(List<RecipeModel> recipeModels){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(Browse_Recipes.this));
        recipeAdapter = new RecipeAdapter(recipeModels);
        binding.recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


}