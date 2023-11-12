package com.example.tncooking.recipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tncooking.MainActivity;
import com.example.tncooking.R;
import com.example.tncooking.WatchVideo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailRecipesActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference _myRef;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipes);

        Bundle bundle = getIntent().getExtras();
        if(bundle==null)
            return;
        Recipe r = (Recipe) bundle.get("object_person");

        TextView RecipesName = findViewById(R.id.RecipesName);
        TextView Introduce = findViewById(R.id.Introduce);
        TextView guide = findViewById(R.id.guide);
        ImageView Avatar = findViewById(R.id.Avatar);
        ScrollView scrollView = findViewById(R.id.scrollView);
        VideoView TutorialVideo = findViewById(R.id.TutorialVideo);
        TextView Ingredients = findViewById(R.id.Ingredients);
        Button Cook = findViewById(R.id.LetCook);
        Button Back = findViewById(R.id.BACK);
        Button Home = findViewById(R.id.HOME);
        Picasso.with(this).load(r.getAvatar()).into(Avatar);


//        Avatar.setImageResource(r.getAvatar());
        RecipesName.setText(r.getName());
        Introduce.setText(r.getIntroduce());
        Ingredients.setText(r.getIngredient());
        guide.setText(r.getGuide());




        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailRecipesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailRecipesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailRecipesActivity.this, WatchVideo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_recipe", r);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}