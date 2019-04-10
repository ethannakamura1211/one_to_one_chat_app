package com.example.tashyaalberto.test1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText passwd,email;
    Button login;
    FirebaseAuth mAuth;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        toolbar=(Toolbar) findViewById(R.id.toolbar);
        passwd=(EditText) findViewById(R.id.passwd);
        email=(EditText) findViewById(R.id.email);
        login=(Button) findViewById(R.id.btn_login);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString() ;
                String txt_passwd=passwd.getText().toString();

                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_passwd))
                {
                    Toast.makeText(LoginActivity.this,"All fields need to be filled",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(txt_email,txt_passwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(LoginActivity.this,MainChatActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Login failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
