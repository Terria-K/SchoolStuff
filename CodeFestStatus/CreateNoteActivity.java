package com.example.notetakingapp;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateNoteActivity extends AppCompatActivity {
    private EditText titleText;
    private EditText multilineText;
    private Button delButton;
    private boolean isEditing;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        titleText = (EditText)findViewById(R.id.note_title);
        multilineText = (EditText)findViewById(R.id.note_content);
        delButton = (Button)findViewById(R.id.delete);

        Bundle bundle = getIntent().getExtras();
        delButton.setVisibility(View.GONE);
        isEditing = bundle.getBoolean("Editing", false);
        if (isEditing) {
            titleText.setText(bundle.getString("Title"));
            multilineText.setText(bundle.getString("Content"));
            id = bundle.getInt("ID");
            delButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        save();
    }

    public void onBack(View view) {
        save();
        finish();
    }

    public void onDelete(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", (x, y) -> {
                    if (Database.getInstance().delete(id)) {
                        Toast.makeText(CreateNoteActivity.this, "Note Deleted",
                                Toast.LENGTH_LONG).show();


                        finish();
                    }
                })
                .setNegativeButton("No", (x, y) -> {

                })
                .create();

        dialog.show();
    }

    private void save() {
        Editable editTitle = titleText.getText();
        String title = "";
        if (editTitle != null) {
            title = editTitle + "";
        }
        if (title.isEmpty()) {
            return;
        }
        String content = multilineText.getText()+"";

        Database.getInstance().AddTitle(title);
        Database.getInstance().AddContents(content);

        if (isEditing) {
            if (Database.getInstance().update(id)) {
                Toast.makeText(CreateNoteActivity.this, "Note Updated",
                        Toast.LENGTH_LONG).show();
            }
            return;
        }
        if (Database.getInstance().build()) {
            Toast.makeText(CreateNoteActivity.this, "Note Created", Toast.LENGTH_LONG).show();
        }
    }
}
