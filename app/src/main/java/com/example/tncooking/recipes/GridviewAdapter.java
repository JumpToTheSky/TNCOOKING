package com.example.tncooking.recipes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tncooking.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridviewAdapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;
    private int mTypeCustom;
    ArrayList<Recipe> lstRecipe;
    public GridviewAdapter(Context context, ArrayList<Recipe> lstRecipe, int mTypeCustom){
        mLayoutInflater = LayoutInflater.from(context);
        this.mTypeCustom = mTypeCustom;
        this.lstRecipe = lstRecipe;

    }

    @Override
    public int getCount() {
        return lstRecipe == null ? 0 : lstRecipe.size();
    }

    @Override
    public Object getItem(int i) {
        return lstRecipe.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Integer.parseInt(lstRecipe.get(i).getName());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView avatar;
        return null;
    }
}
