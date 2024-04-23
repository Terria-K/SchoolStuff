package com.example.notetakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NoteManagement extends AppCompatActivity {
    private FloatingActionButton fab;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_management);
        Database.init(this);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        listView = (ListView)findViewById(R.id.cardlist);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateNoteActivity.class);
            intent.putExtra("Editing", false);
            startActivity(intent);
        });

        listView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            int id = 1;
            try {
                TextView idText = view.findViewById(R.id.text3);
                id = Integer.parseInt(idText.getText().toString().trim());

                Cursor res = Database.getInstance().getID(id);
                StringBuffer buffer0 = new StringBuffer();
                StringBuffer buffer1 = new StringBuffer();
                while (res.moveToNext()) {
                    buffer0.append(res.getString(0));
                    buffer1.append(res.getString(1));
                }

                Intent intent = new Intent(this, CreateNoteActivity.class);
                intent.putExtra("Title", buffer0.toString());
                intent.putExtra("Content", buffer1.toString());
                intent.putExtra("ID", id);
                intent.putExtra("Editing", true);
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Cursor res = Database.getInstance().getAllData();
        List<HashMap<String, String>> results = new ArrayList<>();
        if (res.getCount() == 0) {
            SimpleAdapter adapter = new SimpleAdapter(this,
                    results, R.layout.notes, new String[] {
                    "First Line", "Second Line", "Third Line"},
                    new int[] { R.id.text1, R.id.text2, R.id.text3});

            listView.setAdapter(adapter);
            return;
        }

        HashMap<String, String[]> map = new HashMap<>();

        while (res.moveToNext()) {
            StringBuffer buffer0 = new StringBuffer();
            StringBuffer buffer1 = new StringBuffer();
            StringBuffer buffer2 = new StringBuffer();
            buffer0.append(res.getString(0));
            buffer1.append(res.getString(1));
            buffer2.append(res.getString(2));
            map.put(buffer0.toString(), new String[] { buffer1.toString(), buffer2.toString()});
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                results, R.layout.notes, new String[] {
                        "First Line", "Second Line", "Third Line"},
                new int[] { R.id.text1, R.id.text2, R.id.text3});

        Iterator it = map.entrySet().iterator();

        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();

            resultsMap.put("Third Line", pair.getKey().toString());
            String[] val = (String[])pair.getValue();
            resultsMap.put("First Line", val[0]);
            resultsMap.put("Second Line", val[1]);
            results.add(resultsMap);
        }
        listView.setAdapter(adapter);
    }
}