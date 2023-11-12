package com.example.tncooking.navi_fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tncooking.Login;
import com.example.tncooking.MainActivity;
import com.example.tncooking.R;
import com.example.tncooking.recipes.Recipe;
import com.example.tncooking.recipes.RecipeAdapter;
import com.example.tncooking.recipes.RecipeAdapterRow;
import com.example.tncooking.type.A;
import com.example.tncooking.type.Au;
import com.example.tncooking.type.Viet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }
    RecyclerView rvRecipe1;
    RecyclerView rvRecipe2;
    RecyclerView rvRecipe3;
    RecyclerView rvRecipe4;

    ArrayList<Recipe> lstRecipes2;
    ArrayList<Recipe> lstRecipes3;
    ArrayList<Recipe> lstRecipes4;
    RecipeAdapterRow adapter1;
    RecipeAdapter adapter2;
    RecipeAdapter adapter3;
    RecipeAdapter adapter4;
    int count1,count2,count3;


    ArrayList<String> mKeys = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navi_home, container, false);

        ArrayList<Recipe> lstRecipes1 = new ArrayList<>();
        adapter1 = new RecipeAdapterRow(getContext(), lstRecipes1);
        ArrayList<Recipe> lstRecipes2 = new ArrayList<>();
        adapter2 = new RecipeAdapter(getContext(), lstRecipes2);
        ArrayList<Recipe> lstRecipes3 = new ArrayList<>();
        adapter3 = new RecipeAdapter(getContext(), lstRecipes3);
        ArrayList<Recipe> lstRecipes4 = new ArrayList<>();
        adapter4 = new RecipeAdapter(getContext(), lstRecipes4);



        count1 = 0;
        count2 = 0;
        count3 = 0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tncooking-38146-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe r = snapshot.getValue(Recipe.class);
                if (r != null){
                    lstRecipes1.add(r);
                    adapter1.notifyDataSetChanged();
                    if (r.getType().equals("Á") && count1 < 5){
                        lstRecipes2.add(r);
                        adapter2.notifyDataSetChanged();
                        count1++;
                    }
                    if (r.getType().equals("Âu")&& count2 < 5){
                        lstRecipes3.add(r);
                        adapter3.notifyDataSetChanged();
                        count2++;
                    }
                    if (r.getType().equals("Việt") && count3 < 5){
                        lstRecipes4.add(r);
                        adapter4.notifyDataSetChanged();
                        count3++;
                    }
                    String key = snapshot.getKey();
                    mKeys.add(key);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Recipe r = snapshot.getValue(Recipe.class);
                if (r == null || lstRecipes1 == null || lstRecipes1.isEmpty())
                    return;
                String key = snapshot.getKey();
                int index = mKeys.indexOf(key);
                if (index != -1) {
                    lstRecipes1.remove(index);
                    mKeys.remove(index);
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        rvRecipe1 = view.findViewById(R.id.type);
        rvRecipe2 = view.findViewById(R.id.type2);
        rvRecipe3 = view.findViewById(R.id.type3);
        rvRecipe4 = view.findViewById(R.id.type4);


        rvRecipe1.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvRecipe2.setLayoutManager(linearLayoutManager2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvRecipe3.setLayoutManager(linearLayoutManager3);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvRecipe4.setLayoutManager(linearLayoutManager4);

        rvRecipe1.setAdapter(adapter1);
        rvRecipe2.setAdapter(adapter2);
        rvRecipe3.setAdapter(adapter3);
        rvRecipe4.setAdapter(adapter4);

//________________________________________________________________________________________________________
//________________________________________________________________________________________________________
//button more __________________________________________________________________________________
//_______________________________________________________________________________________________
        Button more2 = view.findViewById(R.id.more2);
        Button more3 = view.findViewById(R.id.more3);
        Button more4 = view.findViewById(R.id.more4);
        Button signOut = view.findViewById(R.id.SignOut);

        more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), A.class);
                startActivity(intent);
            }
        });
        more3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), Au.class);
                startActivity(intent);
            }
        });
        more4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), Viet.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận đăng xuất ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(),
                                "Đã đăng xuất.", Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(getContext(), Login.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();

            }
        });


        return view;
    }

}
