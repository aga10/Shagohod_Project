package com.example.shagohod_project;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karan.churi.PermissionManager.PermissionManager;

import java.security.Permission;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;

    PermissionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(auth == null)
        {
            setContentView(R.layout.activity_main);
            manager = new PermissionManager(){};
            manager.checkAndRequestPermissions(this);
        }
        else
        {
            Intent intent = new Intent(MainActivity.this,MyNavigationActivity.class);
            startActivity(intent);
            finish();
        }

//        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        manager.checkResult(requestCode, permissions, grantResults);
        ArrayList<String> denied_Permissions = manager.getStatus().get(0).denied;

        if(denied_Permissions.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Permission Enabled",Toast.LENGTH_SHORT).show();
        }
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
