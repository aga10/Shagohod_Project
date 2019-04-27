package com.example.shagohod_project;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(auth == null)
        {
            setContentView(R.layout.activity_main);
        }
        else
        {
            Intent intent = new Intent(MainActivity.this,MyNavigationActivity.class);
            startActivity(intent);
            finish();
        }


        setContentView(R.layout.activity_main);
    }

    public void goToLogin(View v) {
        Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(myIntent);
    }

    public void goToRegister(View v) {
        Intent myIntent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(myIntent);
    }

}
