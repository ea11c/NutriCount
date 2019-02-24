package com.example.nutricount;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConfirmMeal extends AppCompatActivity {

    Bundle bundle;
    Cursor cursor;
    TableLayout layout;
    TableLayout.LayoutParams params;
    TableRow row;
    ArrayList<Integer> ingredientKeys;
    ArrayList<EditText> weights;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_meal);
        layout = findViewById(R.id.meal_table);
        bundle = getIntent().getExtras();
        submit = findViewById(R.id.button_meal_submit);
        weights = new ArrayList<>();

        if(bundle != null){
            ingredientKeys = bundle.getIntegerArrayList("Ingredients");
            for(Integer i : ingredientKeys){
                params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                row = new TableRow(this);
                row.setLayoutParams(params);
                cursor = MainActivity.NutriDb.getData(i);
                if(cursor.getCount() !=1){
                    Toast.makeText(ConfirmMeal.this, "Too many ingredients returned", Toast.LENGTH_SHORT).show();
                }
                cursor.moveToFirst();
                TextView tv = new TextView(this);
                tv.setText(cursor.getString(0));
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                row.addView(tv);
                TableRow.LayoutParams etParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                EditText weight = new EditText(this);
                weight.setLayoutParams(etParams);
                weight.setInputType(InputType.TYPE_CLASS_NUMBER);
                weights.add(weight);
                row.addView(weight);
                layout.addView(row, params);
            }
        }
        else{
            Toast.makeText(ConfirmMeal.this, "No ingredients selected", Toast.LENGTH_LONG).show();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor;
                EditText name = findViewById(R.id.newMeal);
                double Fat = 0;
                double Protein = 0;
                double Calories = 0;
                double Sodium = 0;
                double totalWeight = 0;
                double itemWeight;

                if(!name.getText().toString().equals("Name of Meal")) {
                    for (int i = 0; i < weights.size(); i++) {
                        cursor = MainActivity.NutriDb.getData(ingredientKeys.get(i));
                        cursor.moveToFirst();
                        itemWeight = Double.parseDouble(weights.get(i).getText().toString());
                        Fat = Fat + (cursor.getDouble(2) * itemWeight);
                        Protein = Protein + (itemWeight * cursor.getDouble(3));
                        Calories = Calories + (cursor.getDouble(1) * itemWeight);
                        Sodium = Sodium + (cursor.getDouble(4) * itemWeight);
                        totalWeight = totalWeight + itemWeight;
                        cursor.close();
                    }
                    MainActivity.NutriDb.insertData(name.getText().toString(), Fat, Protein, Calories, Sodium, totalWeight);
                    Toast.makeText(ConfirmMeal.this, "Added " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmMeal.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ConfirmMeal.this, "Must enter a name for the meal", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}