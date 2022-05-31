package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText loginBox;
    private EditText passwordBox;

    ArrayAdapter<User> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBox = findViewById(R.id.login);
        passwordBox = findViewById(R.id.password);

//        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                User user =arrayAdapter.getItem(position);
//                if(user!=null) {
//                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//                    intent.putExtra("id", user.getId());
//                    startActivity(intent);
//                }
//            }
//        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        DatabaseAdapter adapter = new DatabaseAdapter(this);
//        adapter.open();
//
//        List<User> users = adapter.getUsers();
//
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
//        //userList.setAdapter(arrayAdapter);
//        adapter.close();
//    }
    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view){

        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }


    public void login(View view){
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<User> users = adapter.getUsers();
        System.out.println(users.toString());
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        String login = loginBox.getText().toString();
        String password = passwordBox.getText().toString();
        int n = users.size();
        boolean flag = false;
        for (int i = 0; i < n; i++){
        User s = users.get(i);

            if (s.getLogin().equals(login) && s.getPassword().equals(password)){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                flag = true;
                break;
            }
        }
        if (!flag){
            Toast.makeText(this, "Неверная пара логин-пароль", Toast.LENGTH_SHORT).show();
        }
    }
}