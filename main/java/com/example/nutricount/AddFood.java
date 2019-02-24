package com.example.nutricount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFood extends Activity {

    EditText editFood, editFat, editProtein, editCalories, editTotalWeight, editSodium;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);
        editCalories = (EditText)findViewById(R.id.editTextCalories);
        editFat = (EditText)findViewById(R.id.editTextFat);
        editProtein = (EditText)findViewById(R.id.editTextProtein);
        editFood = (EditText)findViewById(R.id.editTextFood);
        editTotalWeight = (EditText)findViewById(R.id.editTextGrams);
        editSodium = (EditText)findViewById(R.id.editTextSodium);
        submit = (Button)findViewById(R.id.button_add);
        AddData();
    }

    public void AddData() {
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //validate all fields have data
                        if(isEmpty(editCalories)|| isEmpty(editFat) || isEmpty(editFood) || isEmpty(editProtein) || isEmpty(editSodium) || isEmpty(editTotalWeight))
                            Toast.makeText(AddFood.this, "ERROR: All fields need filled before submitting", Toast.LENGTH_LONG).show();
                        else{
                            boolean isInserted = MainActivity.NutriDb.insertData(editFood.getText().toString(),
                                    Double.parseDouble(editFat.getText().toString()),
                                    Double.parseDouble(editProtein.getText().toString()),
                                    Double.parseDouble(editCalories.getText().toString()),
                                    Double.parseDouble(editSodium.getText().toString()),
                                    Double.parseDouble(editTotalWeight.getText().toString()));

                            if (isInserted = true) {
                                Toast.makeText(AddFood.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                                editCalories.getText().clear();
                                editSodium.getText().clear();
                                editProtein.getText().clear();
                                editFat.getText().clear();
                                editFood.getText().clear();
                                editTotalWeight.getText().clear();
                                Intent intent = new Intent(AddFood.this, ViewFood.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(AddFood.this, "ERROR: Data Not Inserted", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    private boolean isEmpty(EditText etText){
        if (etText.getText().toString().trim().length() == 0)
            return true;
        else
            return false;
    }
}
