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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    EditText username, pass, delete, current_name, new_name;
    RecyclerView view_data_2;
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
        view_data_2 = findViewById(R.id.recycler_view_data);
        btn_add_user = findViewById(R.id.add_user);
        btn_view_data = findViewById(R.id.view_data);
        btn_update = findViewById(R.id.btn_update);
        btn_del = findViewById(R.id.btn_delete);
        helper = new myDBAdapter(this);

        view_data_2.setLayoutManager(new LinearLayoutManager(this));

        btn_add_user.setOnClickListener(v -> {
            addUser(v);
            viewData(v);
        });
        btn_view_data.setOnClickListener(this::viewData);
        btn_update.setOnClickListener(v -> {
            updateData(v);
            viewData(v);
        });
        btn_del.setOnClickListener(v -> {
            deleteData(v);
            viewData(v);
        });
    }

    public void addUser(View view) {
        String name = username.getText().toString();
        String password = pass.getText().toString();
        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Name and password must not be empty",
                    Toast.LENGTH_SHORT).show();
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
    public void viewData(View view) {
        view_data_2.setAdapter(new SQLiteAdapter(helper.getData()));
    }
    public void updateData(View view) {
        String currName = current_name.getText().toString(),
                newName = new_name.getText().toString();
        if (currName.isEmpty() || newName.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Enter data!",
                    Toast.LENGTH_LONG).show();
        } else {
            if (helper.updateName(currName, newName) <= 0) {
                Toast.makeText(getApplicationContext(),
                        "Data was not successfully updated.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Data updated successfully!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    public void deleteData(View view) {
        String uname = delete.getText().toString();
        if (uname.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Enter data!",
                    Toast.LENGTH_LONG).show();
        } else {
            int uuname = helper.deleteData(uname);
            if (uuname <= 0) {
                Toast.makeText(getApplicationContext(),
                        "Unsuccessful!",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Data has been deleted!",
                        Toast.LENGTH_LONG).show();
                delete.setText("");
            }
        }
    }
}