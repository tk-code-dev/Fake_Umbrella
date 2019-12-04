package com.tk.code.fake_umbrella.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.tk.code.fake_umbrella.R;

import java.util.Objects;


public class TitleActivity extends BaseActivity {

    private DialogFragment dialogFragment;
    private FragmentManager fragmentManager;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();

                // DialogFragment を継承したAlertDialogFragmentのインスタンス
                dialogFragment = new AlertDialogFragment();
                dialogFragment.show(fragmentManager, "test alert dialog");
            }
        });
    }


    public static class AlertDialogFragment extends DialogFragment {
        private String[] menulist = {"Email & Password", "Google"};

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //  Rounded corner dialog
            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);

            final AlertDialog alertDialog = alert.create();

            // Dialog Title
            alert.setTitle("Sign In");
            alert.setItems(menulist, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int idx) {
                    // Email & Password
                    if (idx == 0) {
                        Intent intent = new Intent(getActivity(), EmailPasswordActivity.class);
                        startActivity(intent);
                    }
                    // Google Sign In
                    else if (idx == 1) {
                        Intent intent = new Intent(getActivity(), GoogleSignInActivity.class);
                        startActivity(intent);
                    }
                }
            });

            return alert.create();
        }
    }
}


//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.tk.code.fake_umbrella.R;
//
//import java.util.Objects;
//
//public class TitleActivity extends AppCompatActivity {
//
//    Button signInBtn;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_title);
//        Objects.requireNonNull(getSupportActionBar()).hide();
//
//        signInBtn = findViewById(R.id.btnSignIn);
//        signInBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplication(), TableActivity.class);
//                startActivity(intent);            }
//        });
//    }
//}
