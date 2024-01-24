package com.example.muziko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    TextView user,pass;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        user=findViewById(R.id.txt_user);
        pass=findViewById(R.id.txt_pass);
        back=findViewById(R.id.btn_back);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        String password =  bundle.getString("password");
        user.setText(username);
        pass.setText(password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}