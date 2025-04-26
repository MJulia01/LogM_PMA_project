package com.example.myfirstapplication.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapplication.databinding.RecyclerviewRowBinding;
import com.example.myfirstapplication.model.RecipeModel;
import com.example.myfirstapplication.views.Add_Recipy;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    List<RecipeModel> recipeModels;
    public RecipeAdapter(List<RecipeModel> recipeModels) {
        this.recipeModels = recipeModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewRowBinding recyclerviewRowBinding = RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(recyclerviewRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.recipeNameText.setText(recipeModels.get(position).recipeName);
        holder.binding.recipeIngredientsText.setText(recipeModels.get(position).ingredients);
        holder.binding.recipeStepsText.setText(recipeModels.get(position).steps);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Add_Recipy.class);
                intent.putExtra("dataId",recipeModels.get(holder.getAdapterPosition()));
                intent.putExtra("info","old");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewRowBinding binding;
        public ViewHolder(RecyclerviewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
