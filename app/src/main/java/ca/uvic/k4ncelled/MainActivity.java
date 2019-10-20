package ca.uvic.k4ncelled;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;

import ca.uvic.k4ncelled.Backend.Food;
import ca.uvic.k4ncelled.Backend.Fridge;

public class MainActivity extends AppCompatActivity {
    private Fridge myFridge;

    private Spinner sp_selectFood;
    private TextView tv_selectedInfo;
    private Button bt_addFood;
    private Button bt_removeFood;
    private EditText et_foodName;
    private EditText et_purchaseDate;
    private EditText et_expiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myFridge = new Fridge();
        myFridge.addFood("apple", LocalDate.now(), LocalDate.of(2021, 1, 1));
        myFridge.addFood("orange", LocalDate.now(), LocalDate.of(2022, 1, 1));
        myFridge.addFood("banana", LocalDate.now(), LocalDate.of(2020, 1, 1));

        sp_selectFood = findViewById(R.id.sp_selectFood);
        tv_selectedInfo = findViewById(R.id.tv_selectedInfo);
        bt_addFood = findViewById(R.id.bt_addFood);
        bt_removeFood = findViewById(R.id.bt_removeFood);
        et_foodName = findViewById(R.id.et_foodName);
        et_purchaseDate = findViewById(R.id.et_purchaseDate);
        et_expiryDate = findViewById(R.id.et_expiryDate);

        create_sp_selectFood();
        create_bt_addFood();
        create_bt_removeFood();
    }

    private void create_sp_selectFood(){
        ArrayAdapter<Food> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myFridge.getStorage());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_selectFood.setAdapter(arrayAdapter);

        if(arrayAdapter.getCount() == 0){ tv_selectedInfo.setText(R.string.fridge_empty); }

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
                myFridge.addFood(et_foodName.getText().toString(), et_purchaseDate.getText().toString(), et_expiryDate.getText().toString());
                et_foodName.setText("");
                et_purchaseDate.setText("");
                et_expiryDate.setText("");
                create_sp_selectFood();
            }
        });
    }

    private void create_bt_removeFood(){
        bt_removeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFridge.removeFood((Food) sp_selectFood.getSelectedItem());
                create_sp_selectFood();
            }
        });
    }
}
