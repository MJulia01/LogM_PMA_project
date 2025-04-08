package com.example.myfirstapplication;

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


        //Button browseButton = (Button) findViewById(R.id.browseButton);

        //browseButton.setOnClickListener(new View.OnClickListener() {
            //public void onClick(View v) {
                //Log.d("BUTTONS", "User tapped the Supabutton");
            //}
        //});

    }
public void browseN(View view) {
    Log.d("BUTTONS", "User tapped the BUTOOOONNNNN");
}



}