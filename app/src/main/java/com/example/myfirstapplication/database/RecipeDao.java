package com.example.myfirstapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfirstapplication.model.RecipeModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface RecipeDao {
    @Query("select * from RecipeModel")
    Flowable<List<RecipeModel>> getAll();

    @Insert
    Completable insert(RecipeModel recipeModel);

    @Delete
    Completable delete(RecipeModel recipeModel);

    @Update
    Completable update(RecipeModel recipeModel);

}
