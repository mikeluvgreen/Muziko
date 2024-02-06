package com.example.muziko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView user,pass,retypepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user = findViewById(R.id.utUsername);
        pass = findViewById(R.id.utPassword);
        retypepass = findViewById(R.id.utRePassword);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        String password =  bundle.getString("password");
        user.setText(username);
        pass.setText(password);


    }
}