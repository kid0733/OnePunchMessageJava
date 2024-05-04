package com.example.onepunchmessenger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    //Retrieving the UI objects:
    // Initiating container variables
    ImageView logo;
    TextView nameL,nameR,owner;


    //Creating animation names:
    Animation topAnim,bottomAnim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // casting into variables
        logo=findViewById(R.id.logoImg);
        nameL=findViewById(R.id.logoName1);
        nameR=findViewById(R.id.logoName2);
        owner=findViewById(R.id.owner);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //applying animations
        logo.setAnimation(topAnim);
        nameR.setAnimation(topAnim);
        nameL.setAnimation(topAnim);
        owner.setAnimation(bottomAnim);




        //send to next activity(registration)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}