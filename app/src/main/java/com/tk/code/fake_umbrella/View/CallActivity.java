package com.tk.code.fake_umbrella.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tk.code.fake_umbrella.R;

import static android.Manifest.permission.CALL_PHONE;

public class CallActivity extends AppCompatActivity {

    Button btnYes, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        btnYes = findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = getIntent();
                String digits = "tel:" + intent.getStringExtra("CALLNUMBER");
                Log.d("dialnum", digits);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(digits));

                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }


                          }
        });

        btnNo = findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

//        btnYes.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        });

    }
}
