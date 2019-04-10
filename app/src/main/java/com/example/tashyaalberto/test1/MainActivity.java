package com.example.tashyaalberto.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button reg,log;
    FirebaseUser firebaseuser;


    @Override
    protected void onStart() {
        super.onStart();

        firebaseuser=FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseuser!=null){
            Intent intent =new Intent(MainActivity.this,MainChatActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        reg = (Button) findViewById(R.id.register);
        log = (Button) findViewById(R.id.login);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }
}
