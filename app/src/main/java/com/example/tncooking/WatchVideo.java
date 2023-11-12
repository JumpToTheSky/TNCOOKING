package com.example.tncooking;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.tncooking.recipes.DetailRecipesActivity;
import com.example.tncooking.recipes.Recipe;

public class WatchVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video);

        Bundle bundle = getIntent().getExtras();
        if(bundle==null)
            return;
        Recipe r = (Recipe) bundle.get("object_recipe");

        VideoView TutorialVideo = findViewById(R.id.TutorialVideo);
        TutorialVideo.setVideoPath("android.resource://" + getPackageName() + "/" + r.getGuide_video());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(TutorialVideo);
        TutorialVideo.setMediaController(mediaController);
        TutorialVideo.start();

    }
}