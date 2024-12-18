package com.example.mpistask03;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SetPathActivity extends AppCompatActivity {

    public Button authorBtn, callTaxiBtn;
    public EditText fromInput, toInput, dateInput, timeInput, passengersInput, carTypeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_path);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromInput = findViewById(R.id.setPathInputFrom);
        toInput = findViewById(R.id.setPathInputTo);
        dateInput = findViewById(R.id.setPathInputDate);
        timeInput = findViewById(R.id.setPathInputTime);
        passengersInput = findViewById(R.id.setPathInputPassengers);
        carTypeInput = findViewById(R.id.setPathInputCarType);

        // Call Taxi
        callTaxiBtn = findViewById(R.id.setPathBtnCall);

        callTaxiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String from = fromInput.getText().toString();
                String to = toInput.getText().toString();
                String date = dateInput.getText().toString();
                String time = timeInput.getText().toString();
                String passengers = passengersInput.getText().toString();
                String carType = carTypeInput.getText().toString();

                if (from.isEmpty() || to.isEmpty() || date.isEmpty() || time.isEmpty() || passengers.isEmpty() || carType.isEmpty()) {
                    showAlertDialog("Error", "please fill all fields");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("from", from);
                intent.putExtra("to", to);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("passengers", passengers);
                intent.putExtra("carType", carType);
                setResult(RESULT_OK, intent);
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
        new AlertDialog.Builder(SetPathActivity.this)
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