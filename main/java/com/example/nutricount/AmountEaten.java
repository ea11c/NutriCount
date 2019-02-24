package com.example.nutricount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AmountEaten extends AppCompatActivity {

    TextView foodEaten;
    double Fat;
    double Protein;
    double Sodium;
    double Calories;
    double Weight;
    EditText Amount;
    Button Calc;
    TextView fatC;
    TextView proteinC;
    TextView sodiumC;
    TextView caloriesC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_eaten);

        foodEaten = findViewById(R.id.food_eaten);
        foodEaten.setText(getIntent().getExtras().getString("Food"));
        Amount = findViewById(R.id.editTextAmount);
        fatC = findViewById(R.id.textViewFat);
        proteinC = findViewById(R.id.textViewProtein);
        sodiumC = findViewById(R.id.textViewSodium);
        caloriesC = findViewById(R.id.textViewCalories);

        Calc = findViewById(R.id.button_calc);
        Calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Amount.getText().toString().trim().length() == 0){
                    Toast.makeText(AmountEaten.this, "Please enter the weight eaten", Toast.LENGTH_SHORT).show();
                }
                else{
                    Weight = Double.parseDouble(Amount.getText().toString());
                    caloriesC.setText(Double.toString(Weight * Calories));
                    fatC.setText(Double.toString(Weight * Fat));
                    proteinC.setText(Double.toString(Weight * Protein));
                    sodiumC.setText(Double.toString((Weight * 1000) * Sodium));
                    Amount.getText().clear();
                }
            }
        });

        Fat = Double.parseDouble(getIntent().getExtras().getString("Fat"));
        Protein = Double.parseDouble(getIntent().getExtras().getString("Protein"));
        Sodium = Double.parseDouble(getIntent().getExtras().getString("Sodium"));
        Calories = Double.parseDouble(getIntent().getExtras().getString("Calories"));
    }
}
