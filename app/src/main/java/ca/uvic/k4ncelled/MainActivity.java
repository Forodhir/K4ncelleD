package ca.uvic.k4ncelled;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;

import ca.uvic.k4ncelled.Backend.Food;
import ca.uvic.k4ncelled.Backend.Fridge;

public class MainActivity extends AppCompatActivity {
    private Spinner sp_selectFood;
    private TextView tv_selectedInfo;
    private FloatingActionButton bt_addFood;
    private ConstraintLayout lo_addFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Fridge myFridge = new Fridge();
        myFridge.addFood("apple", LocalDate.now(), LocalDate.of(2021, 1, 1));
        myFridge.addFood("orange", LocalDate.now(), LocalDate.of(2022, 1, 1));
        myFridge.addFood("banana", LocalDate.now(), LocalDate.of(2020, 1, 1));

        sp_selectFood = findViewById(R.id.sp_selectFood);
        tv_selectedInfo = findViewById(R.id.tv_selectedInfo);
        bt_addFood = findViewById(R.id.bt_addFood);
        lo_addFood = findViewById(R.id.lo_addFood);

        create_sp_selectFood(myFridge);
        create_bt_addFood();
    }

    private void create_sp_selectFood(Fridge myFridge){
        ArrayAdapter<Food> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myFridge.getStorage());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_selectFood.setAdapter(arrayAdapter);

        sp_selectFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Food selected = (Food) sp_selectFood.getSelectedItem();
                tv_selectedInfo.setText(selected.toString() + "\nPurchase date: "
                        + selected.getPurchaseDate() + "\nExpiry date: "
                        + selected.getExpiryDate());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void create_bt_addFood(){
        bt_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lo_addFood.setVisibility(View.VISIBLE);
            }
        });
    }
}
