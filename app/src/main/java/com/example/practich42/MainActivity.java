package com.example.practich42;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.TextView);
        button = findViewById(R.id.button);

        // Создаем или открываем базу данных
        database = openOrCreateDatabase("UserDB", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, name TEXT)");

        // Добавляем тестовые данные
        database.execSQL("INSERT INTO Users (name) VALUES ('John Doe')");
        database.execSQL("INSERT INTO Users (name) VALUES ('Jane Smith')");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        Cursor cursor = database.rawQuery("SELECT * FROM Users", null);
        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            builder.append("ID: ").append(id).append(", Name: ").append(name).append("\n");
        }
        cursor.close();

        textView.setText(builder.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}