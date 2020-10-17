package com.jcupzz.mycoviddiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import es.dmoral.toasty.Toasty;

public class Already_Registered extends AppCompatActivity {
    Button in;
    private FirebaseAuth firebaseAuth;
    String userID;
    private EditText name;
    private EditText pass;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already__registered);

        in = findViewById(R.id.al_in);

        name =  findViewById(R.id.edUID);
        pass =  findViewById(R.id.edUPASS);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


      //  getSharedPreferences("email_save", MODE_PRIVATE).edit().putString("email",emails).commit();



        if (user != null) {

            startActivity(new Intent(Already_Registered.this, MainActivity.class));

            finish();
        }

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = name.getText().toString().trim();
                sharedPreferences = getSharedPreferences("email_save", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.commit();
                validate(name.getText().toString().trim(), pass.getText().toString().trim());

            }
        });

    }

    private void validate(final String userName, final String userPass) {

        firebaseAuth.signInWithEmailAndPassword(userName, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    String userID;
                    userID = firebaseAuth.getCurrentUser().getUid();
                    SharedPreferences sharedPreferencesss = getSharedPreferences("uid_save", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferencesss.edit();
                    editor.putString("uid", userID);
                    editor.commit();
                    Toasty.success(Already_Registered.this, "Login Successful", Toast.LENGTH_SHORT, true).show();
                    finish();
                    startActivity(new Intent(Already_Registered.this, MainActivity.class));
                }
                else{
                    Toasty.error(Already_Registered.this, "Login Failed", Toast.LENGTH_SHORT, true).show();
                }

            }

        });


    }





@Override
public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Login_Info.class);
        startActivity(intent);
        finish();
        }
        }