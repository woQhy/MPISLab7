package com.example.mpistask03;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class AuthActivity extends AppCompatActivity {

    public Button authorBtn, authBtn;
    public TextInputEditText authInputName, authInputLastname, authInputPhone;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authBtn = findViewById(R.id.authBtn);

        authInputName = findViewById(R.id.authInputName);
        authInputLastname = findViewById(R.id.authInputLastname);
        authInputPhone = findViewById(R.id.authInputPhone);

        // Check user
        sharedPreferences = getSharedPreferences("TaxiApp", MODE_PRIVATE);
        String userName = sharedPreferences.getString("name", "");
        String userLastname = sharedPreferences.getString("lastname", "");
        String userPhone = sharedPreferences.getString("phone", "");

        if (!userName.isEmpty() || !userLastname.isEmpty() || !userPhone.isEmpty()) {
            authBtn.setText(R.string.auth_btn_login);
            authInputName.setText(userName);
            authInputLastname.setText(userLastname);
            authInputPhone.setText(userPhone);
        }

        // Auth User
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = authInputName.getText().toString();
                String lastname = authInputLastname.getText().toString();
                String phone = authInputPhone.getText().toString();

                if (name.isEmpty() || lastname.isEmpty() || phone.isEmpty()) {
                    showAlertDialog("Error", "please fill all fields");
                    return;
                }

                sharedPreferences.edit()
                        .putString("name", name)
                        .putString("lastname", lastname)
                        .putString("phone", phone)
                        .apply();

                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Show Author
        authorBtn = findViewById(R.id.authorBtn);

        authorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Разработал", getString(R.string.author));
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(AuthActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}