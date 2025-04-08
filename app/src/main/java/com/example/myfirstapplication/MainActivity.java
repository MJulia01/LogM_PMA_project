package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = (Button) findViewById(R.id.addRecipyButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the addRecipyButton");
                Intent intent = new Intent(MainActivity.this, Add_Recipy.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.browseButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the browseButton");
                Intent intent = new Intent(MainActivity.this, Browse_Recipes.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.shakeITButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the shakeITButton");
                Intent intent = new Intent(MainActivity.this, Shake_Recipes.class);
                startActivity(intent);
            }
        });


    }




}