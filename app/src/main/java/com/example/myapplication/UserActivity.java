package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class UserActivity extends AppCompatActivity {

    private EditText loginBox;
    private EditText passwordBox;
    private Button delButton;

    private DatabaseAdapter adapter;
    private long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        loginBox = findViewById(R.id.login);
        passwordBox = findViewById(R.id.password);
        delButton = findViewById(R.id.deleteButton);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            User user = adapter.getUser(userId);
            loginBox.setText(user.getLogin());
            passwordBox.setText(user.getPassword());
            adapter.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){

        String login = loginBox.getText().toString();
        try {
            String password = passwordBox.getText().toString();
            User user = new User(userId, login, password);

            adapter.open();
            if (userId > 0) {
                adapter.update(user);
            } else {
                adapter.insert(user);
            }
            adapter.close();
            goHome();
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void delete(View view){

        adapter.open();
        adapter.delete(userId);
        adapter.close();
        goHome();
    }
    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}