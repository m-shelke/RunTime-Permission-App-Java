package com.example.runtimepermissionappjava;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_SMS;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int RED_CODE = 110;
    Button reqBtn;

//    Live Template demo example
    private static final int LiveTemplate = 0;

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


        reqBtn = findViewById(R.id.reqBtn);

        reqBtn.setOnClickListener(v -> {

            if (checkPer()) {
                Toast.makeText(MainActivity.this, "Permission already Granted", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{ACCESS_FINE_LOCATION, READ_SMS}, RED_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            int location = grantResults[0];
            int sms = grantResults[1];

            boolean checkLoc = location == PackageManager.PERMISSION_GRANTED;
            boolean checkMsg = sms == PackageManager.PERMISSION_GRANTED;

            if (checkLoc && checkMsg) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkPer() {
        int resultLocation = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);

        int resultSMS = ActivityCompat.checkSelfPermission(this, READ_SMS);

        return resultLocation == PackageManager.PERMISSION_GRANTED && resultSMS == PackageManager.PERMISSION_GRANTED;  //both must true
    }
}