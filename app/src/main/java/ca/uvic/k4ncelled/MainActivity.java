package ca.uvic.k4ncelled;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;

import ca.uvic.k4ncelled.Backend.Food;
import ca.uvic.k4ncelled.Backend.Fridge;
import ca.uvic.k4ncelled.R;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Fridge myFridge = new Fridge();
        myFridge.addFood("apple", LocalDate.now(), LocalDate.of(2021, 1, 1));
        myFridge.addFood("orange", LocalDate.now(), LocalDate.of(2022, 1, 1));
        myFridge.addFood("banana", LocalDate.now(), LocalDate.of(2020, 1, 1));

        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);

        ArrayAdapter<Food> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myFridge.getStorage());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(((Food) spinner.getSelectedItem()).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
