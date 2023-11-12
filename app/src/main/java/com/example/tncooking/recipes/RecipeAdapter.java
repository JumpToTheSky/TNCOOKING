package com.example.tncooking.recipes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tncooking.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private ArrayList<Recipe> lstRecipes;
    private Context mcontext;
    StorageReference storageReference = FirebaseStorage.getInstance("gs://tncooking-38146.appspot.com").getReference();


    public RecipeAdapter(Context context, ArrayList<Recipe> lstRecipes){
        this.mcontext = context;
        this.lstRecipes = lstRecipes;
    }
    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = lstRecipes.get(position);
        if (recipe == null) return;


        holder.name.setText(recipe.getName());
//        holder.avatar.setImageResource(recipe.getAvatar());
        Picasso.with(mcontext).load(recipe.getAvatar()).into(holder.avatar);
        holder.clickDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newviews = recipe.getViews() + 1;
                recipe.setViews(newviews);
                onclickStarted(recipe);
            }
        });
    }

    private void onclickStarted(Recipe recipe) {
        Intent i =new Intent(mcontext, DetailRecipesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_person", recipe);
        i.putExtras(bundle);
        mcontext.startActivity(i);
    }

    @Override
    public int getItemCount() {
        if (lstRecipes!=null)
            return lstRecipes.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        LinearLayout clickDetail;
        public ViewHolder(@NonNull View view){
            super(view);
            avatar = view.findViewById(R.id.img_food);
            name = view.findViewById(R.id.recipe_name);
            clickDetail = view.findViewById(R.id.ClickDetail);

        }
    }
}
