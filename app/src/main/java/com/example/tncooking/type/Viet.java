package com.example.tncooking.type;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tncooking.R;
import com.example.tncooking.recipes.Recipe;
import com.example.tncooking.recipes.RecipeAdapter;
import com.example.tncooking.recipes.RecipeAdapterRow;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Viet extends AppCompatActivity {
    RecyclerView rvViet;
    ArrayList<Recipe> lstViet;
    ArrayList<Recipe> result4;
    RecipeAdapterRow Viet_adapter;
    ArrayList<String> mKeys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viet);

        lstViet = new ArrayList<>();
        Viet_adapter = new RecipeAdapterRow(this, lstViet);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tncooking-38146-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe r = snapshot.getValue(Recipe.class);
                if (r != null && r.getType().equals("Việt")){
                    lstViet.add(r);
                    Viet_adapter.notifyDataSetChanged();
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
                if (r == null || lstViet == null || lstViet.isEmpty())
                    return;
                String key = snapshot.getKey();
                int index = mKeys.indexOf(key);
                if (index != -1) {
                    lstViet.remove(index);
                    mKeys.remove(index);
                }
                Viet_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        result4 = lstViet;

        rvViet = findViewById(R.id.rvViet);
        rvViet.setLayoutManager(new LinearLayoutManager(this));

        Viet_adapter = new RecipeAdapterRow(this, lstViet);
        rvViet.setAdapter(Viet_adapter);

        SearchView searchView = findViewById(R.id.searchViet);
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
    }
    private void filterList(String text) {
        ArrayList<Recipe> filteredList= new ArrayList<>();
        for (Recipe recipe : lstViet){
            if (recipe.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(recipe);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show();
            Viet_adapter.setFilteredList(filteredList);
        }else {
            Viet_adapter.setFilteredList(filteredList);
        }
    }
}