package com.example.nutricount;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHelper NutriDb;
    Button ChangeAdd;
    Button ChangeView;
    Button ChangeMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NutriDb = new DatabaseHelper(this);
        ChangeAdd = (Button)findViewById(R.id.button_change_add);
        ChangeView = (Button)findViewById(R.id.button_view);
        ChangeMeal = (Button)findViewById(R.id.button_meal);
        ChangeForm();
        ViewForm();
        MealForm();
    }

    public void ChangeForm() {
        ChangeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFood.class);
                startActivity(intent);
            }
        });
    }

    public void ViewForm(){
        ChangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewFood.class);
                startActivity(intent);
            }
        });
    }

    public void MealForm(){
        ChangeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeal.class);
                startActivity(intent);
            }
        });
    }


}
