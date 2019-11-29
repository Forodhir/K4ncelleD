package ca.uvic.k4ncelled;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.Locale;

import ca.uvic.k4ncelled.Backend.Food;
import ca.uvic.k4ncelled.Backend.Fridge;
import ca.uvic.k4ncelled.Data.Data;

public class MainActivity extends AppCompatActivity {
    private Fridge fridge;
    private Data data;

    private ConstraintLayout activeLayout;

    private ConstraintLayout lo_browse;
    private ConstraintLayout lo_add;
    private ConstraintLayout lo_data;
    private LinearLayout lo_scroll;

    private BottomNavigationView nm_navigationView;

    private EditText et_foodName;
    private EditText et_purchaseDate;
    private EditText et_expiryDate;
    private EditText et_price;
    private Button bt_addFood;
    private TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new Data(getApplicationContext());

        fridge = data.load();

        assignComponents();

        create_nm_navigationView();
        create_et_purchaseDate();
        create_et_expiryDate();
        create_bt_addFood();

        nm_navigationView.setSelectedItemId(R.id.mi_browse);

    }

    private void assignComponents(){
        nm_navigationView = findViewById(R.id.nm_navigationView);
        lo_browse = findViewById(R.id.lo_browse);
        lo_add = findViewById(R.id.lo_add);
        lo_data = findViewById(R.id.lo_data);
        lo_scroll = findViewById(R.id.lo_scroll);
        et_foodName = findViewById(R.id.et_foodName);
        et_purchaseDate = findViewById(R.id.et_purchaseDate);
        et_expiryDate = findViewById(R.id.et_expiryDate);
        et_price = findViewById(R.id.et_price);
        bt_addFood = findViewById(R.id.bt_addFood);
        tv_data = findViewById(R.id.tv_data);
    }

    private void updateBrowse(){
        lo_scroll.removeAllViews();
        for(Food food : fridge.getStorage()){
            TextView textView = new TextView(this);
            textView.setText(String.format(Locale.CANADA,"%s\nPurchased: %s\nExpires: %s\nPrice: $%.2f",
                    food.getName(), food.getPurchaseDate().toString(), food.getExpiryDate().toString(), food.getValueCents() / 100.0));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,50);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(R.drawable.layout_bg);
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            textView.setTextSize(24);
            textView.setPadding(80, 24, 24, 24);

            lo_scroll.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            lo_scroll.setDividerPadding(50);

            lo_scroll.addView(textView);
        }
    }

    private void updateData(){
        tv_data.setText(String.format(Locale.CANADA, "Items in fridge: %d\n\nTotal value in fridge: $%.2f\n\n" +
                "Value wasted: $%.2f", fridge.getStorage().size(), fridge.getTotalValueCents() / 100.0,
                fridge.getValueWastedCents() / 100.0));
    }

    private void create_nm_navigationView(){
        nm_navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(activeLayout != null){
                    activeLayout.setVisibility(View.GONE);
                }
                if(menuItem.getItemId() == R.id.mi_browse){
                    activeLayout = lo_browse;
                    updateBrowse();
                }
                else if(menuItem.getItemId() == R.id.mi_add){
                    activeLayout = lo_add;
                }
                else if(menuItem.getItemId() == R.id.mi_data){
                    activeLayout =  lo_data;
                    updateData();
                }
                activeLayout.setVisibility(View.VISIBLE);
                return true;
            }
        });
        nm_navigationView.setSelectedItemId(R.id.mi_browse);
    }

    private void create_et_purchaseDate(){
        et_purchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_purchaseDate.setText(LocalDate.of(year, month + 1, dayOfMonth).toString());
                    }
                },
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth()).show();
            }
        });
    }

    private void create_et_expiryDate(){
        et_expiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_expiryDate.setText(LocalDate.of(year, month + 1, dayOfMonth).toString());
                    }
                },
                        LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth()).show();
            }
        });
    }

    private void create_bt_addFood(){
        bt_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(et_price.getText().toString().replace(".",""));
                if(!et_price.getText().toString().contains(".")){
                    value *= 100;
                }
                fridge.addFood(et_foodName.getText().toString(), et_expiryDate.getText().toString(),
                        et_expiryDate.getText().toString(), value
                        , null);

                nm_navigationView.setSelectedItemId(R.id.mi_browse);
                et_foodName.setText("");
                et_purchaseDate.setText("");
                et_expiryDate.setText("");
                et_price.setText("");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        data.save(fridge);
    }
}
