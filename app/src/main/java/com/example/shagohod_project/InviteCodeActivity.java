package com.example.shagohod_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InviteCodeActivity extends AppCompatActivity {

    String name,email,password,date,isSharing,code;
    Uri imageUri;
    TextView t1;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    String userId;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1 = (TextView)findViewById(R.id.textView);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_images");

        Intent myIntent = getIntent();
        if(myIntent!=null)
        {
            name = myIntent.getStringExtra("name");
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
            code = myIntent.getStringExtra("code");
            isSharing = myIntent.getStringExtra("isSharing");
            imageUri = myIntent.getParcelableExtra("imageUri");
        }

        t1.setText(code);

    }


    public void registerUser(View v)
    {
        progressDialog.setMessage("Please wait while an account is being created for you.");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //insert user data in real-time fire base database

                            CreateUser createUser = new CreateUser(name,email,password,code,"false","N/A","N/A","N/A");

                            user = auth.getCurrentUser();
                            userId = user.getUid();

                            reference.child(userId).setValue(createUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                StorageReference sr = storageReference.child(user.getUid() + ".jpg");
                                                    sr.putFile(imageUri)
                                                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                    if(task.isSuccessful())
                                                                    {
                                                                        String download_image_path = task.getResult().getDownloadUrl().toString();
                                                                        reference.child(user.getUid()).child("imageUri").setValue(download_image_path)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if(task.isSuccessful())
                                                                                                {
                                                                                                    progressDialog.dismiss();
                                                                                                    Toast.makeText(getApplicationContext(),"User registered successfully",Toast.LENGTH_SHORT).show();
                                                                                                    Intent myIntent = new Intent(InviteCodeActivity.this,UserLocationActivity.class);
                                                                                                    startActivity(myIntent);
                                                                                                }
                                                                                                else
                                                                                                {
                                                                                                    progressDialog.dismiss();
                                                                                                    Toast.makeText(getApplicationContext(),"An error occurred while creating account",Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }
                                                                                        });
                                                                    }
                                                                }
                                                            });


                                            }
                                            else
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(),"Registering user was unsuccessful",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        }
                    }
                });

    }


}
