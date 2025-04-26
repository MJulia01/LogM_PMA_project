package com.example.myfirstapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.database.RecipeDao;
import com.example.myfirstapplication.database.RecipeDatabase;
import com.example.myfirstapplication.databinding.ActivityAddRecipyBinding;
import com.example.myfirstapplication.model.RecipeModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Add_Recipy extends AppCompatActivity {
EditText editTextRecipeName, editTextIngredients, editTextInstructions;
Button button_Add;

    private ActivityAddRecipyBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecipeModel recipeModel;
    RecipeDao recipeDao;
    RecipeDatabase recipeDatabase;

    private String recipeName;
    private String ingredients;
    private String steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddRecipyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recipeDatabase = Room.databaseBuilder(getApplicationContext(),RecipeDatabase.class, "Recipes").allowMainThreadQueries().build();
        recipeDao = recipeDatabase.recipeDao();

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        if(info.equals("new")){
            binding.updateButton.setVisibility(View.GONE);
            binding.deleteButton.setVisibility(View.GONE);
        }else {
            binding.saveButton.setVisibility(View.GONE);
            recipeModel = (RecipeModel) intent.getSerializableExtra("dataId");

            binding.recipetitleText.setText(recipeModel.recipeName);
            binding.ingredientsText.setText(recipeModel.ingredients);
            binding.stepsText.setText(recipeModel.steps);
        }
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private void insertData(){
        recipeName = binding.recipetitleText.getText().toString();
        ingredients = binding.ingredientsText.getText().toString();
        steps = binding.stepsText.getText().toString();
        recipeModel = new RecipeModel(recipeName,ingredients,steps);

        compositeDisposable.add(recipeDao.insert(recipeModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Add_Recipy.this::handleResponse));
    }
    private void deleteData(){
        compositeDisposable.add(recipeDao.delete(recipeModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Add_Recipy.this::handleResponse));
    }
    private void updateData(){
        recipeModel.recipeName = binding.recipetitleText.getText().toString();
        recipeModel.ingredients = binding.ingredientsText.getText().toString();
        compositeDisposable.add(recipeDao.update(recipeModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Add_Recipy.this::handleResponse));
    }
    private void handleResponse(){
        Intent intent = new Intent(Add_Recipy.this, Browse_Recipes.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        compositeDisposable.clear();
    }

}