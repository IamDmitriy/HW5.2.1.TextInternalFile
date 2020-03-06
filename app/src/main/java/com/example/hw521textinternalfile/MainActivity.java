package com.example.hw521textinternalfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "user_data.txt";

    private Button btnLogin;
    private Button btnRegistration;
    private EditText edtLogin;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistration = findViewById(R.id.btnRegistration);
        edtLogin = findViewById(R.id.edtLogin);
        edtPassword = findViewById(R.id.edtPassword);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtLogin.getText()) || TextUtils.isEmpty(edtPassword.getText())) {
                    showToast(getString(R.string.error_empty_fields));
                    return;
                }

                FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);


            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
