package com.example.nutricount;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewFood extends AppCompatActivity {
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_food);

        listItem = new ArrayList<>();

        foodList = findViewById(R.id.food_list);

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor res = MainActivity.NutriDb.getData(position+1);
                if(res.getCount() == 1){
                    res.moveToFirst();
                    Bundle mBundle = new Bundle();
                    mBundle.putString("Food", res.getString(0));
                    mBundle.putString("Calories",res.getString(1));
                    mBundle.putString("Fat",res.getString(2));
                    mBundle.putString("Protein", res.getString(3));
                    mBundle.putString("Sodium", res.getString(4));
                    Intent intent = new Intent(ViewFood.this, AmountEaten.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
                else
                    Toast.makeText(ViewFood.this, "No data available", Toast.LENGTH_LONG).show();
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

            foodList.setAdapter(adapter);
        }
    }
}
