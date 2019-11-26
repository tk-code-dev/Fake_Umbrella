package com.tk.code.fake_umbrella.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tk.code.fake_umbrella.R;

public class TitleActivity extends AppCompatActivity {

    Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        getSupportActionBar().hide();


        signInBtn = findViewById(R.id.btnSignIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), TableActivity.class);
                startActivity(intent);            }
        });
    }
}
