package com.example.tncooking.navi_fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tncooking.R;
import com.example.tncooking.recipes.Recipe;
import com.example.tncooking.recipes.RecipeAdapterRow;
import com.example.tncooking.type.A;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Search extends Fragment {
    RecyclerView rvAll;
    ArrayList<Recipe> lstAll;
    ArrayList<Recipe> resultAll;
    RecipeAdapterRow All_adapter;
    ArrayList<String> mKeys = new ArrayList<>();

    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navi_search, container, false);

        lstAll = new ArrayList<>();
        All_adapter = new RecipeAdapterRow(getContext(), lstAll);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tncooking-38146-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe r = snapshot.getValue(Recipe.class);
                if (r != null){
                    lstAll.add(r);
                    All_adapter.notifyDataSetChanged();
                    String key = snapshot.getKey();
                    mKeys.add(key);

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Recipe r = snapshot.getValue(Recipe.class);
                if (r == null || lstAll == null || lstAll.isEmpty())
                    return;
                String key = snapshot.getKey();
                int index = mKeys.indexOf(key);
                if (index != -1) {
                    lstAll.remove(index);
                    mKeys.remove(index);
                }
                All_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        resultAll = new ArrayList<>();
        resultAll.addAll(lstAll);
        rvAll = view.findViewById(R.id.rvAll);
        rvAll.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAll.setAdapter(All_adapter);


        SearchView searchView = view.findViewById(R.id.searchAll);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
        return view;
    }

    private void filterList(String text) {
        ArrayList<Recipe> filteredList= new ArrayList<>();
        for (Recipe recipe : lstAll){
            if (recipe.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(recipe);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
            All_adapter.setFilteredList(filteredList);
        }else {
            All_adapter.setFilteredList(filteredList);
        }
    }
}
