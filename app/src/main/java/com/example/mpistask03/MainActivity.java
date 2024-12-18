package com.example.mpistask03;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public Button authorBtn, setPath, callTaxi;
    public SharedPreferences sharedPreferences;
    public TextView mainTitle, mainPhone, mainNoPath, mainPathFromTo, mainPathDatetime, mainPathInfo;
    public LinearLayout mainPathLayout;

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

        mainPathLayout = findViewById(R.id.mainPathLayout);

        mainNoPath = findViewById(R.id.mainNoPath);
        mainPathFromTo = findViewById(R.id.mainPathFromTo);
        mainPathDatetime = findViewById(R.id.mainPathDatetime);
        mainPathInfo = findViewById(R.id.mainPathInfo);

        // Set Data
        sharedPreferences = getSharedPreferences("TaxiApp", MODE_PRIVATE);
        String userName = sharedPreferences.getString("first name", "");
        String userLastname = sharedPreferences.getString("lastname", "");
        String userPhone = sharedPreferences.getString("phone number", "");

        mainTitle = findViewById(R.id.mainTitle);
        mainTitle.setText("Welcome, " + userName + " " + userLastname + "!");

        mainPhone = findViewById(R.id.mainPhone);
        mainPhone.setText(userPhone);

        // Call Set Path activity
        setPath = findViewById(R.id.mainBtnSetPath);

        setPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetPathActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Call Taxi
        callTaxi = findViewById(R.id.mainBtnCallTaxi);

        callTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Success", "Your taxi on the route: " + mainPathFromTo.getText().toString() + " on the way;\nThank You for choosing us!");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mainNoPath.setVisibility(View.GONE);
            mainPathLayout.setVisibility(View.VISIBLE);
            callTaxi.setEnabled(true);

            String from = data.getStringExtra("from");
            String to = data.getStringExtra("to");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String passengers = data.getStringExtra("passengers");
            String carType = data.getStringExtra("carType");

            mainPathFromTo.setText(from + "→" + to);
            mainPathDatetime.setText(date + ", " + time);
            int passengerCount = Integer.parseInt(passengers);
            String passengerText = passengerCount >= 2 ? passengers + " passengers" : passengers + " passenger";

            mainPathInfo.setText(passengerText + ", " + carType);

            Log.d("TaxiApp", data.getStringExtra("from"));
            Log.d("TaxiApp", data.getStringExtra("to"));
            Log.d("TaxiApp", data.getStringExtra("date"));
            Log.d("TaxiApp", data.getStringExtra("time"));
            Log.d("TaxiApp", data.getStringExtra("passengers"));
            Log.d("TaxiApp", data.getStringExtra("carType"));
        }
    }

    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(MainActivity.this)
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