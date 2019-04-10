package com.example.tashyaalberto.test1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username,passwd,email;
    Button register;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=(EditText) findViewById(R.id.username);
        passwd=(EditText) findViewById(R.id.passwd);
        email=(EditText) findViewById(R.id.email);
        register=(Button) findViewById(R.id.btn_register);
        toolbar=(Toolbar) findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_uname = username.getText().toString();
                String txt_email=email.getText().toString();
                String txt_password=passwd.getText().toString();

                if(TextUtils.isEmpty(txt_uname)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password))
                {
                    Toast.makeText(RegisterActivity.this,"All fields need to be filled",Toast.LENGTH_SHORT).show();

                }else if (txt_password.length()<6)
                {
                    Toast.makeText(RegisterActivity.this,"Password must be atleast 6 characters",Toast.LENGTH_SHORT).show();
                }
                else {
                    register(txt_uname,txt_email,txt_password);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void register(final String username, String email, String passwd)
    {
        mAuth.createUserWithEmailAndPassword(email,passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userid = user.getUid();

                            reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username",username);
                            hashMap.put("imageURL","default");
                            hashMap.put("status","offline");
                            hashMap.put("search",username.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this,MainChatActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });

    }
}
