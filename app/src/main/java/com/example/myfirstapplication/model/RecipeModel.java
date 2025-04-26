package com.example.myfirstapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class RecipeModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "recipe_name")
    public String recipeName;

    @ColumnInfo(name = "ingredients")
    public String ingredients;

    @ColumnInfo(name = "steps")
    public String steps;

    public RecipeModel(String recipeName, String ingredients, String steps) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}