package com.example.hw521textinternalfile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtLogin.getText()) ||
                        TextUtils.isEmpty(edtPassword.getText())) {
                    showToast(getString(R.string.error_empty_fields));
                    return;
                }

                String login = edtLogin.getText().toString();
                String password = edtPassword.getText().toString();

                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = openFileInput(FILE_NAME);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (fileInputStream == null) {
                    showToast("Файл не найден"); //TODO вынести в ресурсы
                    return;
                }

                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = null;
                try {
                    line = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (line != null) {
                    String[] splitLine = line.split(";");
                    String curLogin = splitLine[0];
                    String curPassword = splitLine[1];

                    if (curLogin.equals(login) && curPassword.equals(password)) {
                        showToast("Пользователь найден, вход выполнен успешно");
                        return;
                    }

                    try {
                        line = bufferedReader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                showToast("Неправильная комбинация логина и пароля");
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtLogin.getText()) ||
                        TextUtils.isEmpty(edtPassword.getText())) {
                    showToast(getString(R.string.error_empty_fields));
                    return;
                }

                String login = edtLogin.getText().toString();
                String password = edtPassword.getText().toString();

                FileOutputStream fileOutputStream = null; //Выходной поток от нас , для записи в файл
                try {
                    if (new File(getFilesDir(), FILE_NAME).exists()) {
                        fileOutputStream = openFileOutput(FILE_NAME, MODE_APPEND);
                    } else {
                        fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //TODO Как может быть не найден файл, если он создаётся?
                }

                if (fileOutputStream == null) {
                    showToast("Файл не найден"); //TODO вынести в ресурсы
                    return;
                }

                //TODO почему не требуют обработки IO исключений
                //Записыватель в этот выходной поток записи в файл
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                try {
                    bufferedWriter.write(login + ";" + password + "\n");
                } catch (IOException e) {
                    showToast(getString(R.string.error));
                    e.printStackTrace();
                }

                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                showToast("Регистрация прошла успешно!");

            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
