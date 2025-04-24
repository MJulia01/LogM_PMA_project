package com.example.myfirstapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Added_Recipies {
    @Entity
    public class RecipeDatabase {
        @PrimaryKey
        public int uid;

        @ColumnInfo(name = "recipe_name")
        public String recipeName;

        @ColumnInfo(name = "ingredients_name")
        public String ingredientsNeeded;
    }

}
