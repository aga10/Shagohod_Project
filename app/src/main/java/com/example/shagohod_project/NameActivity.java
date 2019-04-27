package com.example.shagohod_project;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameActivity extends AppCompatActivity {
    String email,password;
    EditText e5_name;
    CircleImageView circleImageView;

    Uri resultUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        e5_name = (EditText)findViewById(R.id.editText5);
        circleImageView = (CircleImageView)findViewById(R.id.circleImageView);

        Intent myIntent = getIntent();
        if(myIntent!=null)
        {
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
        }
    }

    public void generateCode(View v)
    {
        Date myDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random r = new Random();

        int n = 100000 + r.nextInt(900000);
        String code = String.valueOf(n);

        if (resultUri!=null)
        {
            Intent myIntent = new Intent(NameActivity.this,InviteCodeActivity.class);
            myIntent.putExtra("name",e5_name.getText().toString());
            myIntent.putExtra("email",email);
            myIntent.putExtra("password",password);
            myIntent.putExtra("date",date);
            myIntent.putExtra("isSharing",false);
            myIntent.putExtra("code",code);
            myIntent.putExtra("imageUri","resultUri");

            startActivity(myIntent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please choose an image",Toast.LENGTH_SHORT).show();
        }

    }
}
