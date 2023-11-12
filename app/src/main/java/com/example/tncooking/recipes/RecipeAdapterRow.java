package com.example.tncooking.recipes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tncooking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapterRow extends RecyclerView.Adapter<RecipeAdapterRow.ViewHolder> {

    private ArrayList<Recipe> lstRecipes;
    private Context mcontext;

    public RecipeAdapterRow(Context context, ArrayList<Recipe> lstRecipes) {
        this.mcontext = context;
        this.lstRecipes = lstRecipes;
    }

    @NonNull
    @Override
    public RecipeAdapterRow.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterRow.ViewHolder holder, int position) {
        Recipe recipe = lstRecipes.get(position);
        if (recipe == null) return;

        holder.name.setText(recipe.getName());
//        holder.avatar.setImageResource(recipe.getAvatar());
        Picasso.with(mcontext).load(recipe.getAvatar()).into(holder.avatar);
        holder.intro.setText(recipe.getIntroduce());
        holder.Started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickStarted(recipe);
            }
        });
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickStarted(recipe);
            }
        });
    }

    private void onclickStarted(Recipe recipe) {
        Intent i = new Intent(mcontext, DetailRecipesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_person", recipe);
        i.putExtras(bundle);
        mcontext.startActivity(i);
    }

    @Override
    public int getItemCount() {
        if (lstRecipes != null)
            return lstRecipes.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        TextView intro;
        Button Started;
        LinearLayout click;

        public ViewHolder(@NonNull View view) {
            super(view);
            avatar = view.findViewById(R.id.Avatar);
            name = view.findViewById(R.id.RecipesName);
            intro = view.findViewById(R.id.Introduce);
            Started = view.findViewById(R.id.Started);
            click = view.findViewById(R.id.onClick);

        }
    }

    public void setFilteredList(ArrayList<Recipe> filteredList) {
        this.lstRecipes = filteredList;
        notifyDataSetChanged();
    }
}
