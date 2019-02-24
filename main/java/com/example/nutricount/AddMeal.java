package com.example.nutricount;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddMeal extends AppCompatActivity {
    ArrayList<String> listItem;
    ArrayList<Integer> ingredientKeys;
    ArrayAdapter adapter;
    Adapter lvadapter;
    ListView ingredientList;
    Bundle bundle;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        listItem = new ArrayList<>();
        ingredientKeys = new ArrayList<>();
        ingredientList = findViewById(R.id.ingredient_list);
        lvadapter = ingredientList.getAdapter();
        bundle = new Bundle();
        submit = findViewById(R.id.meal_submit);

        ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             if(ingredientKeys.contains(position+1)){
                 ingredientKeys.remove((position+1));
                 Toast.makeText(AddMeal.this, "Removed " + MainActivity.NutriDb.getFood(position+1), Toast.LENGTH_LONG).show();
             }
             else{
                 ingredientKeys.add(position+1);
                 Toast.makeText(AddMeal.this, "Added " + MainActivity.NutriDb.getFood(position+1), Toast.LENGTH_LONG).show();
             }
         }
     });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putIntegerArrayList("Ingredients", ingredientKeys);
                Intent intent = new Intent(AddMeal.this, ConfirmMeal.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        viewData();
    }


    private void viewData(){
        Cursor cursor = MainActivity.NutriDb.getAllData();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data available", Toast.LENGTH_LONG).show();
        }
        else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);

           ingredientList.setAdapter(adapter);
        }
    }
}
