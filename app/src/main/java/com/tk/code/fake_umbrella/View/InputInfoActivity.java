package com.tk.code.fake_umbrella.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tk.code.fake_umbrella.Model.Customer;
import com.tk.code.fake_umbrella.R;

public class InputInfoActivity extends AppCompatActivity {

    EditText nameET, contactET, phoneET, locationET, numEmployeesET;
    Button addBtn, cancelBtn, deleteBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String strName = "", strContact= "", strPhone= "", strLocation= "";
    int intNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);

        nameET = findViewById(R.id.customerET);
        contactET = findViewById(R.id.contactET);
        phoneET = findViewById(R.id.phoneET);
        locationET = findViewById(R.id.locationET);
        numEmployeesET = findViewById(R.id.numEmployeeET);

        addBtn = findViewById(R.id.addBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        Intent intent = getIntent();
        String BackBtn = intent.getStringExtra("INTENT_ADD");
        deleteBtn.setText(BackBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = nameET.getText().toString();
                strContact = contactET.getText().toString();
                strPhone = phoneET.getText().toString();
                strLocation = locationET.getText().toString();
                intNum = Integer.parseInt(numEmployeesET.getText().toString());
                if (strName.isEmpty()){
                    Snackbar.make(view, "Add Information", Snackbar.LENGTH_SHORT).show();
                }
                DatabaseReference postsRef = myRef.child("customer");
                DatabaseReference newPostRef = postsRef.push();

                newPostRef.setValue(new Customer(strName, strContact, strPhone, strLocation, intNum));

                Snackbar.make(view, "Added", Snackbar.LENGTH_SHORT).show();
                clearTextInput();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTextInput();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void clearTextInput() {
        nameET.setText("");
        contactET.setText("");
        phoneET.setText("");
        locationET.setText("");
        numEmployeesET.setText("");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(InputInfoActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
