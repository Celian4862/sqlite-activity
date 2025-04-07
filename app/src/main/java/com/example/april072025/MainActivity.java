package com.example.april072025;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText username, pass, delete, current_name, new_name;
    Button btn_view_data, btn_add_user, btn_del, btn_update;
    myDBAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.ed_username);
        pass = findViewById(R.id.ed_password);
        delete = findViewById(R.id.ed_delete_data);
        current_name = findViewById(R.id.ed_current_name);
        new_name = findViewById(R.id.ed_new_name);
        btn_add_user = findViewById(R.id.add_user);
        btn_view_data = findViewById(R.id.view_data);
        btn_update = findViewById(R.id.btn_update);
        btn_del = findViewById(R.id.btn_delete);
        helper = new myDBAdapter(this);

        btn_add_user.setOnClickListener(this::addUser);
    }

    public void addUser(View view) {
        String name = username.getText().toString();
        String password = pass.getText().toString();
        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "name and password must not be empty", Toast.LENGTH_SHORT).show();
        } else {
            long id = helper.insertData(name, password);
            if (id <= 0) {
                Toast.makeText(getApplicationContext(),
                        "Data was not successfully saved.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Data was successfully saved.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void viewData(View view) {}
    public void updateData(View view) {}
    public void deleteData(View view) {}
}