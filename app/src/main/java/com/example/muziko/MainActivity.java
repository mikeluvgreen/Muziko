package com.example.muziko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button login, createaccount;
    EditText name, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btn_login);
        createaccount = findViewById(R.id.btn_CreateAccount);
        name = findViewById(R.id.edt_user);
        password = findViewById(R.id.edt_pass);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = name.getText().toString().trim();
                String pass = password.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                intent.putExtra("username",user);
                intent.putExtra("password",pass);
                startActivity(intent);
            }
        });

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                password.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater mn = getMenuInflater();
        mn.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mnHome)
        {
            Intent i = new Intent(this,DetailActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}