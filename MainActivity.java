package com.example.notetakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String someHardcodedPasswordBecauseWhyNot = "12345678";
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordText = findViewById(R.id.password);
    }

    public void onNoteManagement(View view) {
        String pass = passwordText.getText()+"";

        if (someHardcodedPasswordBecauseWhyNot.equals(pass)) {
            Intent intent = new Intent(this, NoteManagement.class);
            startActivity(intent);
            return;
        }

        Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_LONG).show();
    }

}